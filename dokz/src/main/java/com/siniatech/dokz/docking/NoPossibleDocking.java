package com.siniatech.dokz.docking;

import com.siniatech.dokz.DokzContainer;

public class NoPossibleDocking implements IDocking {

    @Override
    public void showPotentialPositioning( DokzContainer dokzContainer, DockingGlassPanel glassPanel ) {
        // do nothing
    }

    @Override
    public void applyDocking( DokzContainer dokzContainer ) {
        // do nothing
    }

}
