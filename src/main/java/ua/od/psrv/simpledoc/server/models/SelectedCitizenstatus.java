package ua.od.psrv.simpledoc.server.models;

import ua.od.psrv.simpledoc.server.models.data.Citizenstatus;

public class SelectedCitizenstatus extends Citizenstatus {

    private boolean Selected;

    public boolean isSelected() {
        return Selected;
    }

    public void setSelected(boolean selected) {
        Selected = selected;
    }

    public SelectedCitizenstatus() {
        super();
        this.setSelected(false);
    }

    public SelectedCitizenstatus(Citizenstatus owner) {
        this.setId(owner.getId());
        this.setName(owner.getName());
        this.setDeleted(owner.isDeleted());
        this.setSelected(false);
    }


}
