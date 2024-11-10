package practicum.ingredients;

import java.util.List;

public class IngredientsOrder {
    private String success;
    private List<Ingredients> data;

    public IngredientsOrder (List<Ingredients> data, String success) {
        this.data = data;
        this.success = success;
    }
    public IngredientsOrder() {

    }
    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<Ingredients> getData() {
        return data;
    }

    public void setData(List<Ingredients> data) {
        this.data = data;
    }

}