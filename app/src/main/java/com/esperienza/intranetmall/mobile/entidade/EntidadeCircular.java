package com.esperienza.intranetmall.mobile.entidade;

import java.util.List;

/**
 * Created by ThinkPad on 28/03/2016.
 */
public class EntidadeCircular {

    private List<LeitoresCircular> leitoresCircular;
    private List<Circular> circulares;

    public List<LeitoresCircular> getLeitoresCircular() {
        return leitoresCircular;
    }

    public void setLeitoresCircular(List<LeitoresCircular> leitoresCircular) {
        this.leitoresCircular = leitoresCircular;
    }

    public List<Circular> getCirculares() {
        return circulares;
    }

    public void setCirculares(List<Circular> circulares) {
        this.circulares = circulares;
    }
}
