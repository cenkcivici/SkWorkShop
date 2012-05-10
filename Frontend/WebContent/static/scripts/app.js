function ShoppingCartController(view, service) {
	var instance = this;
	instance.view = view;
	instance.service = service;

	this.start = function() {
		instance.view.init(instance);
	};

	this.removeProduct = function(productUrl) {
		instance.service.removeProduct(productUrl, instance.view.updateCart);
	};

};

function ShoppingCartView() {
	var instance = this;

	this.init = function(controller) {
		instance.controller = controller;
		$(document).on('click','.deleteCartItem', instance.deleteClicked);
		$(document).on('mouseover','.deleteCartItem', instance.cursorToPointer);
		
	};
	
	this.cursorToPointer = function() {
		this.style.cursor = 'pointer';
	};

	this.deleteClicked = function() {
		var productUrl = $(this).attr("id");
		instance.controller.removeProduct(productUrl);
	};

	this.updateCart = function(data) {
		$('#cartContent').html($('#cartContentTable',$(data)).html());
		$('#cartSummary').html($('#cartSummaryTable',$(data)).html());
	};

}

function ShoppingCartService() {
	var instance = this;

	this.removeProduct = function(productUrl, callback) {
		$.ajax ({
				url:appRoot + "/cart/delete/" + productUrl,
				type:'post',
				success: callback});
	};
}

function PaymentController(view, service) {
	var instance = this;
	instance.view = view;
	instance.service = service;

	this.start = function() {
		instance.view.init(instance);
	};
	
	this.displayBonus = function(creditCardNumber,callback) {
		instance.service.getBonusPoints(creditCardNumber,callback);
	};
	
	this.displayPaymentPlan = function(creditCardNumber,callback) {
		instance.service.getInstallmentPlan(creditCardNumber,callback);
	};
	
	this.updateCouponStatus = function(couponString, callback){
		instance.service.updateCouponStatus(couponString, callback);
	};
};

function PaymentView() {
	var instance = this;

	this.init = function(controller) {
		instance.controller = controller;
		$('#creditCard\\.cardNumber').blur(instance.viewPaymentPlan);
		$('#creditCard\\.cardNumber').blur(instance.viewBonus);
		$('#couponString').blur(instance.updateCost);
	};
	
	this.viewPaymentPlan = function() {
		var creditCardNumber = $('#creditCard\\.cardNumber').val();
		instance.controller.displayPaymentPlan(creditCardNumber,instance.updatePaymentPlan);
	};
	
	this.viewBonus = function() {
		var creditCardNumber = $('#creditCard\\.cardNumber').val();
		instance.controller.displayBonus(creditCardNumber,instance.updateBonus);
	};
	
	this.updateCost = function() {
		var couponString = $('#couponString').val();
		instance.controller.updateCouponStatus(couponString, instance.updateCouponStatus);
	};
	
	this.updatePaymentPlan = function(html) {
		$('#availablePlans').html(html);
	};
	
	this.updateBonus = function(html) {
		$('#availableBonusPoints').html(html);
	};
	
	this.updateCouponStatus = function(html) {
		
		var couponStatus = $('#couponStatus',$(html)).html();
		var discountedAmount = $('#discountedAmount',$(html)).html();
		
		$('#couponStatus').html(couponStatus);
		$('#discountedAmount').html(discountedAmount);
		
		if(couponStatus == "Valid"){
			$('#couponStringParam').val($('#couponString').val());
		}else{
			$('#couponStringParam').val("");
		}
		
	};
};

function PaymentService() {
	var instance = this;

	this.getInstallmentPlan = function(creditCardNumber,callback) {
		$.ajax ({
				url:appRoot + "/installmentplan/show",
				data: {'creditCardNumber' : creditCardNumber},
				type:'post',
				success: callback});
	};
	
	this.getBonusPoints = function(creditCardNumber,callback) {
		$.ajax ({
			url:appRoot + "/queryBonus/",
			data: {'creditCardNumber' : creditCardNumber},
			type:'post',
			success: callback});
	};
	
	this.updateCouponStatus = function(couponString, callback){
		$.ajax ({
			url:appRoot + "/payment/useCoupon",
			data: {'couponString' : couponString},
			type:'post',
			success: callback});
	};
}


function OrdersController(view, service) {
	var instance = this;
	instance.view = view;
	instance.service = service;

	this.start = function() {
		instance.view.init(instance);
	};
	
	this.refundOrder = function(id) {
		instance.service.refundOrder(id, instance.view.updateOrders);
	};
};

function OrdersView() {
	var instance = this;

	this.init = function(controller) {
		instance.controller = controller;
		$(document).on('click','.refundOrder', instance.refundClicked);
		
	};
	
	this.refundClicked = function() {
		var id = $(this).attr("id");
		instance.controller.refundOrder(id);
	};

	this.updateOrders = function(data) {
		$('#ordersTable').html(data);
	};

}

function OrdersService() {
	var instance = this;
	
	this.refundOrder = function(id, callback) {
		$.ajax ({
				url:appRoot + "/orders/refund/" + id,
				type:'post',
				success: callback});
	};
}

function SearchController(view, service) {
	var instance = this;
	instance.view = view;
	instance.service = service;

	this.start = function() {
		instance.view.init(instance);
	};
	
	this.searchProduct = function(keyword) {
		instance.service.searchProduct(keyword);
	};
};

function SearchView() {
	var instance = this;

	this.init = function(controller) {
		instance.controller = controller;
		$(document).on('click','#searchButton', instance.search);
		$(document).on('click','#keyword', instance.clearKeyword);
		
	};
	
	this.search = function() {
		var keyword = $('#keyword').val();
		instance.controller.searchProduct(keyword);
	};
	
	this.clearKeyword = function(){
		$('#keyword').val('');
	};

}

function SearchService() {
	var instance = this;
	
	this.searchProduct = function(keyword) {
		
		window.location = appRoot + "/search?keyword="+keyword;
	};
}
