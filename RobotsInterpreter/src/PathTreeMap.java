/* PathTreeMap
Creator: Joshua Chittle
Contributors: 

Description: Represents a sub-tree of a heirarchy mapping to various values.
Values which decend from this node but are not explicitly given a value inherit this nodes value.
*/

class PathTreeMap<KeyType extends Comparable<KeyType>, ValueType>
{
	private ValueType _value;
	private List<KeyValuePair<KeyType, PathTreeMap<KeyType, ValueType> > _keyToNextNode;
	
	public PathTreeMapNode(ValueType value)
	{
		this._value = value;
		this._keyToNextNode = new LinkedList<KeyValuePair<KeyType, PathTreeMap<KeyType, ValueType> >();
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
		
		return this.getNextNode(keys[0]).get(Arrays.copyOfRange<KeyType>(keys, 1, keys.length);
	}
	
	public void put(KeyType[] keys, ValueType value)
	{
		if(keys == null || keys.length == 0)
		{
			this._value = value;
		}
		else if(!this.containsKey(keys[0])
		{
			KeyValuePair<KeyType, PathTreeMap<KeyType, ValueType> element =
				new KeyValuePair<KeyType, PathTreeMap<KeyType, ValueType>(
					keys[0], new PathTreeMapNode(this.get()));
			this._keyToNextNode.add(element);
		}
		
		this.getNextNode(keys[0]).put(Arrays.copyOfRange<KeyType>(keys, 1, keys.length), value);
	}
	
	private ValueType getNextNode(KeyType key)
	{
		for(KeyValuePair<KeyType, PathTreeMapNode<KeyType, ValueType> pair : _keyToNextNode)
		{
			if(pair.getKey().equals(currentKey))
			{
				return	pair.getValue().get(remainingKeys);
			}
		}
		
		return null;
	}
	
	public bool containsKey(KeyType key)
	{
		return this.getNextNode(key) != null;
	}
}
