package com.siniatech.dokz.docking;

import com.siniatech.dokz.DokzContainer;

public interface IDocking {

    void showPotentialPositioning( DokzContainer dokzContainer, DockingGlassPanel glassPanel );

    void applyDocking( DokzContainer dokzContainer );

}
