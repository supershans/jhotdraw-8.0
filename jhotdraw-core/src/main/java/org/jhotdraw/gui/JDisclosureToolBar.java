/**
 * @(#)JDisclosureToolBar.java
 *
 * Copyright (c) 2008 The authors and contributors of JHotDraw.
 * You may not use, copy or modify this file, except in compliance with the 
 * accompanying license terms.
 */
package org.jhotdraw.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import org.jhotdraw.gui.plaf.palette.*;

/**
 * A ToolBar with disclosure functionality.
 *
 * @author Werner Randelshofer
 * @version $Id$
 */
public class JDisclosureToolBar extends JToolBar {
    private static final long serialVersionUID = 1L;

    private JButton disclosureButton;
    public static final String DISCLOSURE_STATE_PROPERTY = "disclosureState";
    public static final String DISCLOSURE_STATE_COUNT_PROPERTY = "disclosureStateCount";

    /** Creates new form. */
    public JDisclosureToolBar() {
        setUI(PaletteToolBarUI.createUI(this));
        initComponents();
    }

    private void initComponents() {
        GridBagConstraints gbc;
        AbstractButton btn;

        setLayout(new GridBagLayout());

        gbc = new GridBagConstraints();
        if (disclosureButton == null) {
            btn = new JButton();
            btn.setUI((PaletteButtonUI) PaletteButtonUI.createUI(btn));
            btn.setBorderPainted(false);
            btn.setIcon(new DisclosureIcon());
            btn.setOpaque(false);
            disclosureButton = (JButton) btn;
            disclosureButton.putClientProperty(DisclosureIcon.CURRENT_STATE_PROPERTY, 1);
            disclosureButton.putClientProperty(DisclosureIcon.STATE_COUNT_PROPERTY, 2);
            disclosureButton.addActionListener(new ActionListener() {

    @Override
                public void actionPerformed(ActionEvent e) {
                    int newState = ((Integer) disclosureButton.getClientProperty(DisclosureIcon.CURRENT_STATE_PROPERTY) + 1) %
                            (Integer) disclosureButton.getClientProperty(DisclosureIcon.STATE_COUNT_PROPERTY);
                    setDisclosureState(newState);
                }
            });
        } else {
            btn = disclosureButton;
        }

        gbc.gridx = 0;
        gbc.insets = new Insets(0, 1, 0, 1);
        gbc.anchor = GridBagConstraints.SOUTHWEST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weighty = 1d;
        gbc.weightx = 1d;
        add(btn, gbc);

        putClientProperty(PaletteToolBarUI.TOOLBAR_INSETS_OVERRIDE_PROPERTY, new Insets(0, 0, 0, 0));
        putClientProperty(PaletteToolBarUI.TOOLBAR_ICON_PROPERTY, new EmptyIcon(10, 8));
    }

    public void setDisclosureStateCount(int newValue) {
        int oldValue = getDisclosureStateCount();
        disclosureButton.putClientProperty(DisclosureIcon.STATE_COUNT_PROPERTY, newValue);
        firePropertyChange(DISCLOSURE_STATE_COUNT_PROPERTY, oldValue, newValue);
    }

    public void setDisclosureState(int newValue) {
        int oldValue = getDisclosureState();
        disclosureButton.putClientProperty(DisclosureIcon.CURRENT_STATE_PROPERTY, newValue);

        removeAll();
        JComponent c = getDisclosedComponent(newValue);
        GridBagConstraints gbc = new GridBagConstraints();
        if (c != null) {
            gbc = new GridBagConstraints();
            gbc.gridx = 1;
            gbc.weightx = 1d;
            gbc.weighty = 1d;
            gbc.fill = GridBagConstraints.BOTH;
            gbc.anchor = GridBagConstraints.WEST;
            add(c, gbc);
            gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.weightx = 0d;
            gbc.insets = new Insets(0, 1, 0, 1);
            gbc.weighty = 1d;
            gbc.fill = GridBagConstraints.NONE;
            gbc.anchor = GridBagConstraints.SOUTHWEST;
            add(disclosureButton, gbc);
        } else {
            gbc = new GridBagConstraints();
            gbc.gridx = 1;
            gbc.weightx = 1d;
            gbc.weighty = 1d;
            gbc.fill = GridBagConstraints.NONE;
            gbc.anchor = GridBagConstraints.SOUTHWEST;
            gbc.insets = new Insets(0, 1, 0, 1);
            add(disclosureButton, gbc);
        }

        invalidate();
        Container parent = getParent();
        while (parent.getParent() != null && !parent.getParent().isValid()) {
            parent = parent.getParent();
        }
        parent.validate();
        repaint();

        firePropertyChange(DISCLOSURE_STATE_PROPERTY, oldValue, newValue);
    }

    public int getDisclosureStateCount() {
        Integer value = (Integer) disclosureButton.getClientProperty(DisclosureIcon.STATE_COUNT_PROPERTY);
        return (value == null) ? 2 : value;
    }

    public int getDisclosureState() {
        Integer value = (Integer) disclosureButton.getClientProperty(DisclosureIcon.CURRENT_STATE_PROPERTY);
        return (value == null) ? 1 : value;
    }

    protected JComponent getDisclosedComponent(int state) {
        return new JLabel(Integer.toString(state));
    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     * /
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
    }// </editor-fold>//GEN-END:initComponents
     */
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
