package com.example.demo.app.customer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Basket;
import com.example.demo.entity.Menu;
import com.example.demo.entity.Orders;
import com.example.demo.entity.Seat;
import com.example.demo.service.BasketService;
import com.example.demo.service.MenuService;
import com.example.demo.service.OrdersService;
import com.example.demo.service.SeatService;

@Controller
@RequestMapping("/customer")
public class CustomerController {

	private final MenuService menuService;
	private final BasketService basketService;
	private final OrdersService ordersService;
	private final SeatService seatService;

	@Autowired
	public CustomerController( MenuService menuService,
			                                  BasketService basketService,
			                                  OrdersService ordersService,
			                                  SeatService seatService) {
		this.menuService = menuService;
		this.basketService = basketService;
		this.ordersService = ordersService;
		this.seatService = seatService;
	}

	/**
     * 全メニューデータを取得し、indexページに表示
     * @param model
     * @param complete
     * @return customer/index.html
     */
	@GetMapping("/index/{seatId}")
	public String getForm(Model model,
			                           @ModelAttribute("complete") String complete,
			                           @PathVariable int seatId) {
		//seatステータスを取得する。
		Seat  seat = new Seat();
		Optional<Seat> seatOpt = seatService.getSeat(seatId);
		if(seatOpt.isPresent()) {
			seat = seatOpt.get();
		}

		//シートステータスに応じて処理を振り分ける。
		switch(seat.getStatus()) {
		case 1:
		case 4:
			return "customer/wait";
		case 3:
			return "customer/pay";
		}

		//menuListをDBから取得。modelに引き渡す。
		List<Menu> menuList = menuService.getAll();
		model.addAttribute("menuList",menuList);

		//basketListをDBから取得。basketShowに詰める。modelに引き渡す。
		List<Basket> basketList = basketService.getAllByCustomerId(1);
		List<BasketShow> basketShowList = makeBasketShowList(basketList,menuList);

		model.addAttribute("basketShowList",basketShowList);
		model.addAttribute("customerId",seatId);

		return "customer/index";
	}

	/**
     * MenuIdを取得し、1件のデータをbasketテーブルに追加
     * @param model
     * @param complete
     * @return customer/index.html
     */
	@PostMapping("/add_basket/{seatId}")
	public String addBasket(Model model,
			                           @RequestParam("menuId") int menuId,
			                           @ModelAttribute("complete") String complete,
			                           @PathVariable int seatId) {

		//Menuを取得(Optionalでラップ)
		Optional<Menu> menuOpt = menuService.getMenu(menuId);
		//MenuOptがnullで無ければ中身を取り出し
		if(menuOpt.isPresent()) {
			Menu menu = menuOpt.get();

			//menuデータをbasketに詰める
			Basket basket =  makeBasket(menu);

			//basketテーブルに追加
			basketService.insert(basket);
		}
		return "redirect:/customer/index/"+seatId;
	}


	/**
     * MenuIdを取得し、basketを削除する
     * @param model
     * @return customer/index.html
     */
	@PostMapping("/basket_del/{seatId}")
	public String delBasket(Model model,
                                         @RequestParam("menuId") int menuId,
                                         @PathVariable int seatId){
		basketService.deleteByMenuid(menuId);

		return "redirect:/customer/index/" + seatId;
	}


	/**
     * customerIdごとのBasketをOrderテーブルに登録
     * @param model
     * @return customer/index.html
     */
	@PostMapping("/basket_order/{seatId}")
	public String orderBasket(Model model,
                                         @RequestParam("customerId") int customerId,
                                         @PathVariable int seatId){
		//customerIdのbasketテーブルを取得
		customerId =1;
		List<Basket> basketList = basketService.getAllByCustomerId(customerId);

		//basketをorderに詰め直す
		List<Orders> ordersList = makeOrders(basketList);

		//orderテーブルに登録
		ordersService.insertOrders(ordersList);

		//basketを削除
		basketService.deleteAllByCostomerId(customerId);

		return "redirect:/customer/index/" + seatId;
	}


	/**
     * 全ordersデータを取得し、履歴ページ(history)に表示
     * @param model
     * @return customer/history.html
     */
	@GetMapping("/history/{seatId}")
	public String getHistory(Model model,
			                             @PathVariable int seatId){
		//ordersListをDBから取得。modelに引き渡す。
		List<Orders> ordersList = ordersService.getAllByCustomerId(seatId);

		int totalPrice =0;
		for(Orders orders : ordersList) {
			totalPrice += orders.getPrice();
		}

		model.addAttribute("ordersList",ordersList);
		model.addAttribute("totalPrice",totalPrice);

		return "customer/history";
	}

	/**
     * 全ordersデータを取得し、履歴ページ(history)に表示
     * @param model
     * @return customer/history.html
     */
	@PostMapping("/pay")
	public String postPay(Model model,
									@RequestParam("customerId") int customerId){
		seatService.updeteStatus(customerId, 3);
		return "redirect:/customer/index/"+customerId;
	}

	@GetMapping("/pay")
	public String getPay(Model model) {
		return "/customer/pay";
	}


	/**
     * MenuのデータをBasketに入れて返す
     * @param menu
     * @return Basket
     */
	private Basket makeBasket(Menu menu) {
		Basket basket = new Basket();
		basket.setCustomerId(1);
		basket.setMenuId(menu.getId());
		basket.setOrderTime(LocalDateTime.now());
		return basket;
	}

    /**
     * BasketListのデータをBasketShowListに入れる
     * Menu情報の取得、集計を行う
     * @param List<Basket> bList
     * @param List<Menu>   mList
     * @return List<BasketShow>
     */
	private List<BasketShow>  makeBasketShowList(List<Basket> basketList,
			                                                                   List<Menu> menuList){
		List<BasketShow> showList = new ArrayList<BasketShow>();


		for(Basket basket : basketList) {
			Boolean  registered = false;
			//商品IDが特定の既にshowListに追加済みかを確認
			for(BasketShow bs : showList) {
				//登録済みの場合、個数を+1。ループを抜ける。
				if(bs.getMenuId() == basket.getMenuId()) {
					bs.setQuantity(bs.getQuantity()+1);
					registered = true;
					bs.setTotalPrice(bs.getPrice() * bs.getQuantity());
					break;
				}
			}
			//showListに未登録の場合、basketShowを追加
			if(! registered) {
				BasketShow basketShow = new BasketShow();
				basketShow.setId(basket.getId());
				basketShow.setCustomerId(basket.getCustomerId());
				basketShow.setQuantity(1);
				for(Menu menu : menuList) {
					if(basket.getMenuId() == menu.getId()) {
						basketShow.setMenuId(menu.getId());
						basketShow.setMenuName(menu.getMenuName());
						basketShow.setPrice(menu.getPrice());
					}
				}
				basketShow.setTotalPrice(basketShow.getPrice() * basketShow.getQuantity());
				showList.add(basketShow);
			}
		}
		return showList;
	}


	/**
     * BasketListのデータをOrdersListに入れる
     * Menu情報の取得、集計を行う
     * @param List<Basket> basketList
     * @List<Orders> makeOrders
     */
	private List<Orders> makeOrders(List<Basket> basketList){
		List<Orders> ordersList = new ArrayList<>();
		for(Basket basket : basketList) {
			Orders orders = new Orders();
			orders.setCustomerId(basket.getCustomerId());
			orders.setMenuId(basket.getMenuId());
			orders.setOrderTime(LocalDateTime.now());
			ordersList.add(orders);
		}
		return ordersList;
	}

}
