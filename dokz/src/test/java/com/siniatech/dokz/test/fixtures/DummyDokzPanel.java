/*******************************************************************************
 * Dokz
 * Copyright (c) 2011-2 Siniatech Ltd  http://www.siniatech.com/products/dokz
 *
 * All rights reserved. This project and the accompanying materials are made 
 * available under the terms of the MIT License which can be found in the root  
 * of the project, and at http://www.opensource.org/licenses/mit-license.php
 *
 ******************************************************************************/
package com.siniatech.dokz.test.fixtures;

import java.awt.Rectangle;

import javax.swing.JComponent;
import javax.swing.JPanel;

import com.siniatech.dokz.DokzManager;
import com.siniatech.dokz.DokzPanel;

public class DummyDokzPanel extends DokzPanel {

    public DummyDokzPanel() {
        super( null, new JPanel(), "Mock Panel" );
    }

    public DummyDokzPanel( Rectangle rectangle ) {
        this();
        setBounds( rectangle );
    }

    @Override
    protected JComponent createButtonBar( DokzManager dokzContainer, String title ) {
        return new JPanel();
    }

}
