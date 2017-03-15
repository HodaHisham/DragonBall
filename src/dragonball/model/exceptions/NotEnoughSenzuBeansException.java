package dragonball.model.exceptions;

import dragonball.model.cell.Collectible;

@SuppressWarnings("serial")
public class NotEnoughSenzuBeansException extends NotEnoughResourcesException {
	public NotEnoughSenzuBeansException() {
		super("SenzuBeans: 0, There are no SenzuBeans available to be used.");
	}
}
