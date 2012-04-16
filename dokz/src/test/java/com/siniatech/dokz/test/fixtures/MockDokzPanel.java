package com.siniatech.dokz.test.fixtures;

import java.awt.Rectangle;

import javax.swing.JComponent;
import javax.swing.JPanel;

import com.siniatech.dokz.DokzManager;
import com.siniatech.dokz.DokzPanel;

public class MockDokzPanel extends DokzPanel {

    public MockDokzPanel() {
        super( null, new JPanel(), "Mock Panel" );
    }

    public MockDokzPanel( Rectangle rectangle ) {
        this();
        setBounds( rectangle );
    }

    @Override
    protected JComponent createButtonBar( DokzManager dokzContainer, String title ) {
        return new JPanel();
    }

}
