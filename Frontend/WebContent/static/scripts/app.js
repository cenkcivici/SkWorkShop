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
	
	this.displayPaymentPlan = function(creditCardNumber,callback) {
		instance.service.getInstallmentPlan(creditCardNumber,callback);
	};
};

function PaymentView() {
	var instance = this;

	this.init = function(controller) {
		instance.controller = controller;
		$('#creditCard\\.cardNumber').blur(instance.viewPaymentPlan);
	};
	
	this.viewPaymentPlan = function() {
		var creditCardNumber = $('#creditCard\\.cardNumber').val();
		instance.controller.displayPaymentPlan(creditCardNumber,instance.updatePaymentPlan);
	};
	
	this.updatePaymentPlan = function(html) {
		$('#availablePlans').html(html);
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
}


function OrdersController(view, service) {
	var instance = this;
	instance.view = view;
	instance.service = service;

	this.start = function() {
		instance.view.init(instance);
	};
	
	this.rejectOrder = function(id) {
		instance.service.rejectOrder(id, instance.view.updateOrders);
	};
};

function OrdersView() {
	var instance = this;

	this.init = function(controller) {
		instance.controller = controller;
		$(document).on('click','.rejectOrder', instance.rejectClicked);
		
	};
	
	this.rejectClicked = function() {
		var id = $(this).attr("id");
		instance.controller.rejectOrder(id);
	};

	this.updateOrders = function(data) {
		$('#ordersTable').html(data);
	};

}

function OrdersService() {
	var instance = this;
	
	this.rejectOrder = function(id, callback) {
		$.ajax ({
				url:appRoot + "/orders/reject/" + id,
				type:'post',
				success: callback});
	};
}
