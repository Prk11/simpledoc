package ua.od.psrv.simpledoc.server.models;

import ua.od.psrv.simpledoc.server.models.data.Citizencategory;

public class SelectedCitizencategory extends Citizencategory {

    private boolean Selected;

    public boolean isSelected() {
        return Selected;
    }

    public void setSelected(boolean selected) {
        Selected = selected;
    }

    public SelectedCitizencategory() {
        super();
        this.setSelected(false);
    }

    public SelectedCitizencategory(Citizencategory owner) {
        this.setId(owner.getId());
        this.setName(owner.getName());
        this.setDeleted(owner.isDeleted());
        this.setSelected(false);
    }


}
