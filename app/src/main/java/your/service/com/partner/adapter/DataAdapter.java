package your.service.com.partner.adapter;

public class DataAdapter
{

    public String cat_id,cate_name;
    private boolean isSelected;

    public DataAdapter(String cat_id, String cate_name) {
        this.cat_id = cat_id;
        this.cate_name = cate_name;

    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getCate_name() {
        return cate_name;
    }

    public void setCate_name(String cate_name) {
        this.cate_name = cate_name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
