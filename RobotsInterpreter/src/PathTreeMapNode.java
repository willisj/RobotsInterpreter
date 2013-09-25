class PathTreeMapNode<KeyType extends Comparable<KeyType>, ValueType>
{
	private ValueType _value;
	private List<KeyValuePair<KeyType, PathTreeMapNode<KeyType, ValueType> > _keyToNextNode;
	
	public PathTreeMapNode(ValueType value)
	{
		this._value = value;
		this._keyToNextNode = new LinkedList<KeyValuePair<KeyType, PathTreeMapNode<KeyType, ValueType> >();
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
			KeyValuePair<KeyType, PathTreeMapNode<KeyType, ValueType> element =
				new KeyValuePair<KeyType, PathTreeMapNode<KeyType, ValueType>(
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
