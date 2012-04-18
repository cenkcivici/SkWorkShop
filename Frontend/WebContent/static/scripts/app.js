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
