package pt.c03ensaios.debolacha.impl;

import java.util.List;
import java.util.Vector;

import anima.annotation.Component;
import anima.component.IRequires;
import anima.component.ISupports;
import anima.component.InterfaceType;
import anima.component.base.ComponentBase;
import pt.c03ensaios.debolacha.inter.IAnimalList;
/**
 * Makes operations with List<String>
 * @author Douglas Afonso
 *
 */
@Component(id="<http://purl.org/dcc/pt.c03ensaios.debolacha.impl.AnimalList>",
	       provides="<http://purl.org/dcc/pt.c03ensaios.debolacha.inter.IAnimalList>")
public class AnimalList extends Vector<String> implements IAnimalList{
	
	private static final long serialVersionUID = 1L;
	private ISupports component;

	/**
	 * Creates an empty AnimalList
	 */
	public AnimalList(){
		this.component = new ComponentBase();
		this.clear();
	}

	/**
	 *Creates a AnimalList from a String[]
	 * @param a String[]
	 */
	public void setList(String[] listaNomes){
		for(int i =0; i < listaNomes.length; i++){
			this.add(listaNomes[i]);
		}
	}
	
	/**
	 * @param outra: other AnimalList.
	 * @return: common elements between this List and other one.
	 */
	public IAnimalList intersec (List<String> outra){
		IAnimalList alist = new AnimalList();

		if(this == null || outra == null)
			return null;

		for(int j=0;j<this.size();j++){
			if(outra.contains(this.get(j))){
				alist.add(this.get(j));
			}
		}

	return alist;
	}
	
	/**
	 * @param outra: the other List<String>
	 * @return: AnimalList that contents all the that exists
	 *	        in this AnimalVector or in the other AnimalList.
	 */
	public IAnimalList uniao (List<String> outra){
		/*Creates an AnimalVector that contents all the animals
		 *that exists in 'outra', but doesn't exist in this*/
		IAnimalList alist = this.complementar(outra);
		
		if(this == null)
			return alist;

		for(int i=0;i<this.size();i++){
			alist.add(this.get(i));
		}
		
		return alist;		
	}
	
	/** 
	 * @param other: other AnimalList.
	 * @return: An AnimalList that contents all the elements that exists
	 *          in 'other', but doesn't exist in this.
	 */
	public IAnimalList complementar(List<String> other){
		IAnimalList alist = new AnimalList();

		if(this == null)
			return (IAnimalList)other;
		if(other == null)
			return null;
		
		for(int i=0; i< other.size(); i++)
			if(!this.contains(other.get(i)))
				alist.add(other.get(i));
		
		return alist;
	}

	@Override
	public String getInstanceId() {
		return component.getInstanceId();
	}

	@Override
	public <T extends ISupports> T queryInterface(String arg0) {
		return component.queryInterface(arg0);
	}

	@Override
    public <T extends ISupports> T queryInterface(String arg0,
                                                  InterfaceType arg1)
    {
        return component.queryInterface(arg0, arg1);
    }
	
    @Override
    public int addRef()
    {
        return component.addRef();
    }
    
    @Override
    public int release()
    {
        return component.release();
    }

    /**
     * @deprecated
     */
    @Override
	public <T extends ISupports> IRequires<T> queryReceptacle(String arg0) {
		return component.queryReceptacle(arg0);
	}
}

