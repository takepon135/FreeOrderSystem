package com.example.demo.app.manage;

import java.awt.image.BufferedImage;
import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.imageio.ImageIO;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.demo.entity.CompleteOrder;
import com.example.demo.entity.Menu;
import com.example.demo.entity.Orders;
import com.example.demo.entity.Seat;
import com.example.demo.service.CompleteOrderService;
import com.example.demo.service.MenuService;
import com.example.demo.service.OrdersService;
import com.example.demo.service.SeatService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;


@Controller
@RequestMapping("/manage")
public class ManageController {
	
	private final MenuService menuService;
	private final OrdersService ordersService;
	private final SeatService seatService;
	private final CompleteOrderService comOrderService;
	
	@Autowired
	public ManageController( MenuService menuService,
			                                OrdersService ordersService,
			                                SeatService seatService,
			                                CompleteOrderService comOrderService
			                                ) {
		this.menuService = menuService;
		this.ordersService = ordersService;
		this.seatService = seatService;
		this.comOrderService = comOrderService;
	}
	
	
	@GetMapping("/login")
	public String showLoginForm() {
		return "manage/login";
	}
	
	@GetMapping("/logout")
	public String showLogoutForm() {
		return "manage/logout";
	}
	
	@GetMapping("/menu/index")
	public String getForm(Model model,
			                           MenuForm menuForm,
			                           @ModelAttribute("complete") String complete) {
		
//		menuForm.setIsNewMenu(true);
		List<Menu> list = menuService.getAll();
		model.addAttribute("menuList",list);
		return "manage/menu/index";
	}
	
	@GetMapping("/menu/form")
	public String getNew(MenuForm menuForm,
			                          Model model) {
		model.addAttribute("title","メニューの新規作成");
		//新規登録か更新かを判断する仕掛け
		menuForm.setIsNewMenu(true);
		return "manage/menu/form";
	}
	@PostMapping("/menu/form")
	public String postConfirmToNew(MenuForm menuForm,
			                          Model model) {
		model.addAttribute("title","メニューの新規作成");
		menuForm.setIsNewMenu(true);
		return "manage/menu/form";
	}
	
	@PostMapping("/menu/confirm")
	public String confirm(@Validated MenuForm menuForm,
			                          BindingResult resurt, 
			                          Model model) {
		if(resurt.hasErrors()) {
			menuForm.setIsNewMenu(true);
			model.addAttribute("title","メニューの新規作成");
			return "manage/menu/form";
		}
		model.addAttribute("title", "確認ページ");
		return "manage/menu/confirm";
	}
	
	@ PostMapping("/menu/new")
	public String postNew(MenuForm menuForm,
			                          Model model) {
		model.addAttribute("title","メニューの新規作成");
		return "manage/menu/form";
	}
	
	
	@PostMapping("/menu/complete")
	public String complete(@Validated MenuForm menuForm,
			                           BindingResult result,
			                           Model model,
			                           RedirectAttributes redirectAttributes) {
		
		Menu menu = makeMenu(menuForm, 0);
		if(! result.hasErrors()) {
			menuService.insert(menu);
			redirectAttributes.addFlashAttribute("complete", "メニューの登録が完了しました。");
			return "redirect:/manage/menu/index";
		}else {
			model.addAttribute("title", "メニューの新規作成");
			redirectAttributes.addFlashAttribute("complete", "メニューの登録が失敗しました。");
			return "manage/menu/index";
		}
	}

    /**
     * 一件メニューデータを取得し、フォーム内に表示
     * @param menuForm
     * @param id
     * @param model
     * @return
     */
	@GetMapping("/menu/{id}")
	public String showUpdate(
			MenuForm menuForm,
			@PathVariable int id,
			Model model) {
		
		//Menuを取得(Optionalでラップ)
		Optional<Menu> menuOpt = menuService.getMenu(id);
		
		//MenuFormに詰め直し
		Optional<MenuForm> menuFormOpt = menuOpt.map(m -> makeMenuForm(m));
		
		//MenuFormがnullで無ければ中身を取り出し
		if(menuFormOpt.isPresent()) {
			menuForm = menuFormOpt.get();
		}
		
		model.addAttribute("menuForm",menuForm);
		List<Menu> list = menuService.getAll();
		model.addAttribute("menuList",list);
		model.addAttribute("menuId",id);
		model.addAttribute("title","更新用フォーム");
		
		return "manage/menu/form";
	}
	
    /**
     * MenuIdを取得し、1件のデータ更新
     * @param menuForm
     * @param result
     * @param model
     * @param redirectAttributes
     * @return
     */
	@PostMapping("/menu/update")
	public String update(
			@Valid @ModelAttribute MenuForm menuForm,
			BindingResult result,
			@RequestParam("menuId") int menuId,
			Model model,
			RedirectAttributes redirectAttributes
			) {
		
		//MenuFormのデータをMenuに格納
		Menu menu = makeMenu(menuForm, menuId);
		
		if(!result.hasErrors()) {
			//更新処理、フラッシュスコープの利用、リダイレクト
			menuService.updete(menu);
			redirectAttributes.addFlashAttribute("complete","更新が完了しました");
			return "redirect:/manage/menu/index";  
		}else {
			redirectAttributes.addFlashAttribute("complete","更新できませんでした。入力内容を再確認してください。");
			return "redirect:/manage/menu/index";
		}
	}

    /**
     * MenuIdを取得し、1件のデータ削除
     * @param id
     * @param model
     * @return
     */
	@PostMapping("/menu/delete")
	public String delete(
			@RequestParam("menuId") int id,
			Model model) {
		
		//Menuを1件削除しリダイレクト
		menuService.deleteById(id);
		
		return "redirect:/manage/menu/index";  	
	}
	
    /**
     * 業務メニューを表示
     * @param model
     * @return
     */
	@GetMapping("/business")
	public String buziness(Model model) {
		List<Orders> ordersList = ordersService.getAllByCustomerId(1);
		model.addAttribute("ordersList",ordersList);
		List<Seat> seatList = seatService.getAll();
		model.addAttribute("seatList",seatList);
		return "manage/business";
	}
	
	/**
     * 業務メニューで配達フラグを更新
     * @param model
     * @return
     */
	@PostMapping("/delivered")
	public String PostDelivared(Model model,
                                    		@RequestParam("ordersId") int ordersId) {
		ordersService.updateDeliveryFlg(ordersId);
		return "redirect:/manage/business";
	}
	
	@GetMapping("/seat_detail/{seatId}")
	public String showSeatStatus(@PathVariable int seatId, Model model) {
		
		List<Orders> ordersList = ordersService.getAllByCustomerId(seatId);
		
		int totalPrice =0;
		for(Orders orders : ordersList) {
			totalPrice += orders.getPrice();
		}
		
		model.addAttribute("ordersList",ordersList);
		model.addAttribute("totalPrice",totalPrice);
		model.addAttribute("seatId", seatId);
		
		return "manage/seat_detail";
	}
	
	@PostMapping("seat_status/update")
	public String postSeatUpdate(Model model,
													@RequestParam("seatId") int seatId,
													@RequestParam("status") int status) {
		//スタータス"3"(会計依頼中)→"4"(要あとかたづけ)で確認ページへ
		//それ以外はステータス更新
		Optional<Seat> optSeat = seatService.getSeat(seatId);
		Seat seat = optSeat.get();
		if(seat.getStatus() == 3 &&
			status == 4) {
			model.addAttribute("seatId",seatId);
			model.addAttribute("status",status);
			return "manage/seat_check";
		}
		seatService.updeteStatus(seatId, status);
		return "redirect:/manage/business";
	}
	
	@GetMapping("/seat")
	public String getSeatManage(Model model) {
		// 現在のテーブル数を取得する
		List<Seat> seatList = seatService.getAll();
		int seatQuantity = seatList.size();
		model.addAttribute("seatQuantity",seatQuantity);
		model.addAttribute("seatList",seatList);
		return "manage/seat";
	}
	
	@PostMapping("/paid_update")
	public String postPaidUpdate(@RequestParam("seatId") int seatId,
												  @RequestParam("status") int status) {
		//ordersをcompleteOrderに登録
		List<Orders> ordersList = ordersService.getAllByCustomerId(seatId);
		List<CompleteOrder> comOrderList = makeCompleteOrder(ordersList);
		comOrderService.insertComOrder(comOrderList);
		
		//ordersを削除
		ordersService.deleteAllBySeatId(seatId);
		
		//seat：statusを"4"(後片付け)に変更
		seatService.updeteStatus(seatId, status);
		
		return "redirect:/manage/business";
	}
	
	
	@GetMapping("/seat_change")
	public String getSeatChange(Model model) {
		// 現在のテーブル数を取得する
		List<Seat> seatList = seatService.getAll();
		int seatQuantity = seatList.size();
		model.addAttribute("seatQuantity",seatQuantity);
		return "manage/seat_change";
	}
	
	
	
	@PostMapping("/seat_change")
	public String postSeatChange(Model model,
			                                       int newQuanitity) {
		//更新可否のチェック ステータスが"1"(未使用)か？
		List<Seat> seatList = seatService.getAll();
		int seatQuantity = seatList.size();
		for(Seat seat : seatList) {
			if(seat.getStatus() != 1) {
				model.addAttribute("message","すべてのテーブルが未使用状態でなければ変更できません。");
				model.addAttribute("seatQuantity",seatQuantity);
				return "manage/seat_change";
			}
		}
		//すべてのseatテーブルを削除
		seatService.deleteAll();
		//newQuanitityの個数分seatテーブルを追加
		seatService.insertSeat(newQuanitity);
		//newQuanitityの数文のseatテーブルを作成。
		return "redirect:/manage/seat";
	}
	
	
	@GetMapping("/seat_url/{seatId}")
	public String getSeatUrl(Model model, 
	                                 		@PathVariable int seatId) {
		//urlを取得
		String url = ServletUriComponentsBuilder.fromCurrentRequestUri().toUriString();
		String homeUrl = url.replace("/manage/seat_url/"+seatId,"");
		String seatUrl = homeUrl + "/customer/index/" + seatId;
		//二次元バーコードを生成
	    try {
	        QRCodeWriter writer = new QRCodeWriter();
	        BitMatrix bitMatrix = writer.encode(seatUrl, BarcodeFormat.QR_CODE, 256, 256);
	        BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);
	        String savePath = "src/main/resources/static/images/QRcode_seat" + seatId + ".png";
	        ImageIO.write(image, "png", new File(savePath));
	      } catch (Exception e) {
	        e.printStackTrace();
	      }
	    try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} 
	    model.addAttribute("seatId",seatId);
		return "manage/seat_url";
	}
	
	@GetMapping("/earnings")
	public String getEarnings(Model model) {
		List<CompleteOrder> comList =  comOrderService.getAllComOrder();
		model.addAttribute("comList",comList);
		Map<String, Integer> totalMap = getTotaling(comList);
		model.addAttribute("totalMap",totalMap);
		return "manage/earnings";
	}
	
	private Map<String, Integer> getTotaling(List<CompleteOrder> comList){
		Map<String,Integer> totalMap =  new HashMap<>();
		for(CompleteOrder comOrder : comList) {
			int price = comOrder.getPrice();
			LocalDateTime createdTime = comOrder.getC_create_time();
			String yyyyMM = createdTime.getYear() + "年" + createdTime.getMonthValue() + "月";
			totalMap.put(yyyyMM, totalMap.containsKey(yyyyMM) ? totalMap.get(yyyyMM) + price : price);
		}
		return totalMap;
	}
	
    /**
     * MenuFormのデータをMenuに入れて返す
     * @param menuForm
     * @param menuId 新規登録の場合は0を指定
     * @return
     */
	private Menu makeMenu(MenuForm menuForm, int menuId) {
		Menu menu = new Menu();
		if(menuId != 0) {
			menu.setId(menuId);
		}
//		menu.setShopId(menuForm.getShopId());
		menu.setMenuName(menuForm.getMenuName());
		menu.setDetail(menuForm.getDetail());
		menu.setPrice(menuForm.getPrice());
		return menu;
	}

	
    /**
     * MenuのデータをMenuFormに入れて返す
     * @param menu
     * @return
     */
	private MenuForm makeMenuForm(Menu menu) {
		
		MenuForm menuForm = new MenuForm();
		
//		menuForm.setShopId(menu.getShopId());
		menuForm.setMenuName(menu.getMenuName());
		menuForm.setDetail(menu.getDetail());
		menuForm.setPrice(menu.getPrice());
		menuForm.setIsNewMenu(false);
		
		return menuForm;
	}
	
	/**
     * List<Orders>のデータをList<CompleteOrder>に入れて返す
     * @param List<Orders>
     * @return List<CompleteOrder>
     */
	private List<CompleteOrder> makeCompleteOrder(List<Orders> ordersList) {
		List<CompleteOrder> comOrderList = new ArrayList<>();
		for(Orders orders : ordersList) {
			CompleteOrder comOrder = new CompleteOrder();
			comOrder.setC_seat_id(orders.getCustomerId());
			comOrder.setC_menu_id(orders.getMenuId());
			comOrder.setC_create_time(LocalDateTime.now());
			comOrderList.add(comOrder);
		}
		return comOrderList;
	}

}
