package pt.c02foundations.statistics.s02;

import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import pt.c02foundations.statistics.s01.IStatistics;
import anima.annotation.Component;
import anima.component.IRequires;
import anima.component.view.base.FrameComponentBase;

@Component(id = "<http://purl.org/dcc/pt.c02foundations.statistics.s02.StatisticsGUIComponent>",
           provides = {"<http://purl.org/dcc/anima.component.view.base.IFrameComponent>"},
           requires = {"<http://purl.org/dcc/pt.c02foundations.statistics.s01.IStatistics>"})
public class StatisticsGUIComponent extends FrameComponentBase implements IRequires<IStatistics>
{
    private static final long serialVersionUID = -8876074710418284771L;

    private IStatistics provider = null;
    
    /*
     * Constructor
     *************/
    
    public StatisticsGUIComponent()
    {
        super();
        visual();
    }
    
    /*
     * IRequires<IStatistics> Interface
     **********************************/
    
    public void connect(IStatistics provider)
    {
        this.provider = provider;
        setVisible(true);
    }
    
    /*
     * Form methods
     **************/
    
    private JTextField valueField,
                       averageField;
    
    private void visual()
    {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container contentPane = getContentPane();
        
        SpringLayout layout = new SpringLayout();
        setLayout(layout);
        
        JLabel valueLabel = new JLabel("value");
        add(valueLabel);
        layout.putConstraint(SpringLayout.WEST, valueLabel, 5, SpringLayout.WEST, contentPane);
        layout.putConstraint(SpringLayout.NORTH, valueLabel, 5, SpringLayout.NORTH, contentPane);
        
        valueField = new JTextField(10);
        add(valueField);
        layout.putConstraint(SpringLayout.WEST, valueField, 5, SpringLayout.EAST, valueLabel);
        layout.putConstraint(SpringLayout.NORTH, valueField, 0, SpringLayout.NORTH, valueLabel);
        
        JButton insertButton = new JButton("insert value");
        insertButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                insertValueClicked();
            }
        });
        add(insertButton);
        layout.putConstraint(SpringLayout.WEST, insertButton, 5, SpringLayout.WEST, contentPane);
        layout.putConstraint(SpringLayout.NORTH, insertButton, 5, SpringLayout.SOUTH, valueLabel);
        
        JLabel averageLabel = new JLabel("average");
        add(averageLabel);
        layout.putConstraint(SpringLayout.WEST, averageLabel, 5, SpringLayout.WEST, contentPane);
        layout.putConstraint(SpringLayout.NORTH, averageLabel, 5, SpringLayout.SOUTH, insertButton);
        
        averageField = new JTextField(10);
        averageField.setEnabled(false);
        add(averageField);
        layout.putConstraint(SpringLayout.WEST, averageField, 5, SpringLayout.EAST, averageLabel);
        layout.putConstraint(SpringLayout.NORTH, averageField, 5, SpringLayout.SOUTH, insertButton);
        
        JButton averageButton = new JButton("calculate average");
        averageButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                calculateAverageClicked();
            }
        });
        add(averageButton);
        layout.putConstraint(SpringLayout.WEST, averageButton, 5, SpringLayout.WEST, contentPane);
        layout.putConstraint(SpringLayout.NORTH, averageButton, 5, SpringLayout.SOUTH, averageLabel);
        
        layout.putConstraint(SpringLayout.EAST, contentPane, 5, SpringLayout.EAST, averageField);
        layout.putConstraint(SpringLayout.SOUTH, contentPane, 5, SpringLayout.SOUTH, averageButton);
        
        pack();
    }
    
    private void insertValueClicked()
    {
        if (valueField != null)
        {
            proxyInsertValue(Float.parseFloat(valueField.getText()));
            valueField.setText("");
        }
    }
    
    private void calculateAverageClicked()
    {
        if (averageField != null)
        {
            averageField.setText(Float.toString(proxyAverage()));
        }
    }
    
    protected void proxyInsertValue(float value)
    {
        if (provider != null)
            provider.insertValue(value);
    }
    
    protected float proxyAverage()
    {
        float average = 0f;
        
        if (provider != null)
            average = provider.average();
        
        return average;
    }
    
    protected void updateAverage(float average)
    {
        if (averageField != null)
        {
            averageField.setText(Float.toString(average));
        }
    }
}
