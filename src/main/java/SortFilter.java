public class SortFilter {
    private SortOrder order = SortOrder.ASC;
    private SortField field  = SortField.TITLE;

    SortFilter(SortField field , SortOrder order){
        this.field = field;
        this.order = order;
    }

    public SortField getField() {
        return field;
    }

    public SortOrder getOrder() {
        return order;
    }
}

enum SortOrder{
    ASC, DESC
        };

enum SortField{
    TITLE, AUTHOR , PUBLISHEDYEAR

};