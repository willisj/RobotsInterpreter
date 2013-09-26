import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/* PathTreeMap
Creator: Joshua Chittle
Contributors: Jordan Willis, code debugging

Description: Represents a sub-tree of a hierarchy mapping to various values.
Values which descend from this node but are not explicitly given a value inherit this nodes value.
*/

class PathTreeMap<KeyType extends Comparable<KeyType>, ValueType>
{
	private ValueType _value;
	private List< KeyValuePair< KeyType, PathTreeMap<KeyType, ValueType> > > _keyToSubTree;
	private KeyType currentKey; 
	
	public PathTreeMap(ValueType value)
	{
		this._value = value;
		this._keyToSubTree = new LinkedList< KeyValuePair<KeyType, PathTreeMap<KeyType, ValueType>>>();
	}
	
	public ValueType get()
	{
		return this._value;
	}
	
	public ValueType get(KeyType[] keys)
	{
		if(keys == null || keys.length == 0 || !this.containsKey(keys[0]))
		{
			return this.get();
		}
		
		return this.getSubTree(keys[0])).get(Arrays.copyOfRange(keys, 1, keys.length));
	}
	
	public void put(KeyType[] keys, ValueType value)
	{
		if(keys == null || keys.length == 0)
		{
			this._value = value;
		}
		else if(!this.containsKey(keys[0]))
		{
			KeyValuePair<KeyType, PathTreeMap<KeyType, ValueType>> element =
				new KeyValuePair<KeyType, PathTreeMap<KeyType, ValueType>>(
					keys[0], new PathTreeMap<KeyType, ValueType>(this.get()));
			this._keyToSubTree.add(element);
		}
		
		this.getSubTree(keys[0])).put(Arrays.copyOfRange(keys, 1, keys.length), value);
	}
	
	/* getSubTree(..)
	Input:	Key (outgoing edge of this node).
	Output:	Root node of a Sub-Tree of this Node determined by following the given key.
		Null if this node does not contain the given key.
	*/
	private PathTreeMap<KeyType, ValueType> getSubTree(KeyType key)
	{
		for(KeyValuePair<KeyType, PathTreeMap<KeyType, ValueType>> pair : _keyToSubTree)
		{
			if(pair.getKey().equals(key))
			{
				return	pair.getValue(); 
			}
		}
		
		return null;
	}
	
	boolean containsKey(KeyType key) 
	{
		return this.getSubTree(key) != null;
	}
}
