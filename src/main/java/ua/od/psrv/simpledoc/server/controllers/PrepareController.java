package ua.od.psrv.simpledoc.server.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.servlet.ModelAndView;

public class PrepareController {
    
    private List<String> Prestyle;
    private List<String> Prescript;
    private List<String> Postscript;

    public PrepareController() {
        Prestyle = new ArrayList<>();
        Prescript = new ArrayList<>();
        Postscript = new ArrayList<>();
    }

    public List<String> getPrestyle() {
        return Prestyle;
    }

    protected void setPrestyle(List<String> prestyle) {
        Prestyle = prestyle;
    }

    public List<String> getPrescript() {
        return Prescript;
    }

    protected void setPrescript(List<String> prescript) {
        Prescript = prescript;
    }

    public List<String> getPostscript() {
        return Postscript;
    }

    protected void setPostscript(List<String> postscript) {
        Postscript = postscript;
    }

    public void prepareBundle(ModelAndView model) {
        model.addObject(
            "Prestyle", 
            Prestyle
        );
        model.addObject(
            "Prescript", 
            Prescript
        );
        model.addObject(
            "Postscript", 
            Postscript
        );
    }

    public void clearBundle() {
        Prestyle.clear();
        Prescript.clear();
        Postscript.clear();
    }
    
    public void prepareNewBundle(ModelAndView model) {
        clearBundle();
        prepareBundle(model);
    }
}
