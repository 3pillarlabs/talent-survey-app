package com.tpg.survey.web.enums;

public enum ElementType {
	
	TEXT ("Text"), // any kind of text answer
	RADIOGROUP ("RadioGroup"), // any kind of radio button click answer
	HTML ("Html"), // any kind of line or para or etc, which is actually not a quedtion but a kind of info
	BULLET ("Bullet"),
	RATING ("Rating");
	
	private String type;
	
	private ElementType(String type) {
		this.setType(type);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public static ElementType getValueOf(String stringCellValue) {
		if(stringCellValue.equalsIgnoreCase("Text"))
			return ElementType.TEXT;
		if(stringCellValue.equalsIgnoreCase("RadioGroup"))
			return ElementType.RADIOGROUP;
		if(stringCellValue.equalsIgnoreCase("Html"))
			return ElementType.HTML;
		if(stringCellValue.equalsIgnoreCase("Bullet"))
			return ElementType.BULLET;
		if(stringCellValue.equalsIgnoreCase("Rating"))
			return ElementType.RATING;
		return null;
	}
	
}
