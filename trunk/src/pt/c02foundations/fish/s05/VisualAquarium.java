package pt.c02foundations.fish.s05;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JTextArea;

import pt.c02foundations.fish.s01.IFish;
import anima.annotation.Component;
import anima.component.IRequires;
import anima.component.view.base.FrameComponentBase;

@Component(id="<http://purl.org/dcc/pt.c02foundations.fish.s05.VisualAquarium>",
           provides = {"<http://purl.org/dcc/anima.component.view.base.IFrameComponent>"},
           requires={"<http://purl.org/dcc/pt.c02foundations.fish.s01.IFish>"})
public class VisualAquarium extends FrameComponentBase implements IRequires<IFish>
{
    private static final long serialVersionUID = 680773317442295460L;
    
    private JTextArea textArea = new JTextArea();
    
    public VisualAquarium()
    {
        super();
        
        setLayout(new BorderLayout());
        add(textArea, BorderLayout.CENTER);
        pack();
        
        setSize(200, 100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        setVisible(true);
    }
    
    public void connect(IFish theFish)
    {
        textArea.append(theFish.fishImage());
    }

}
