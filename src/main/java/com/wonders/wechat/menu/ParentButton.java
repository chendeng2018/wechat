package com.wonders.wechat.menu;

/**  
* 
* @author chd 
* @date 2019年4月10日 
*/
public class ParentButton extends Button{
	
	public ParentButton(String name) {
		super(name);
	}
	
	private Button[] sub_button;

    public Button[] getSub_button() {
        return sub_button;
    }

    public void setSub_button(Button[] sub_button) {
        this.sub_button = sub_button;
    }


}
