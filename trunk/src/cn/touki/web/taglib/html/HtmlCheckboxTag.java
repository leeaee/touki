package cn.touki.web.taglib.html;

/**
 * A tag to build html for a 'checkbox' list.
 *
 * @author <A href="mailto:leeaee@gmail.com">Liyi</A>
 * 
 */
public class HtmlCheckboxTag extends HtmlCheckTag {

	private static final long serialVersionUID = 1L;
	//Properties
    private static final String CHECK_TYPE = "checkbox";
    private String selected;
    private int[] slctIndex;

    //Constructor
    public HtmlCheckboxTag() {
    }

    //Methods
    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }

    @Override
    protected boolean isChecked(int i) {
        if (slctIndex == null || slctIndex.length == 0) {
            initSlctIndex();
        }

        return contains(i);
    }

    private boolean contains(int i) {
        for (int j = 0; j < slctIndex.length; j++) {
            if (slctIndex[j] == i) {
                return true;
            }
        }

        return false;
    }

    private void initSlctIndex() {
        if (selected == null || selected.length() == 0) {
            slctIndex = new int[0];
        }
        else {
            String[] tmp = selected.split(",");
            slctIndex = new int[tmp.length];
            for (int j = 0; j < tmp.length; j++) {
                slctIndex[j] = Integer.parseInt(tmp[j]);
            }
        }
    }

    @Override
    protected String getCheckType() {
        return CHECK_TYPE;
    }

}
