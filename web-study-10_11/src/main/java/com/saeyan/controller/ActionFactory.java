package com.saeyan.controller;

public class ActionFactory {

	private static ActionFactory instanse = new ActionFactory();

	private ActionFactory() {
	}

	public static ActionFactory getInstance() {
		return instanse;
	}
								// product_list
	public Action getAction(String command) {
		
		Action action = null;

		System.out.println("ActionFactory : " + command);
		
		if(command.equals("product_list")) {
			action = new ProductListAction();
		}else if(command.equals("product_write")) {
			action = new ProductWriteFromAction();
		}else if(command.equals("product_write_action")) {
			action = new ProductwriteAction();
		}

		return action;
}
}
