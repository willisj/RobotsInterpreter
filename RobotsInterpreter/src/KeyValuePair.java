class KeyValuePair<KeyType, ValueType>
{
	private KeyType _key;
	private ValueType _value;
	
	public KeyValuePair(KeyType key, ValueType value)
	{
		this._key = key;
		this._value = value;
	}
	
	public KeyType getKey()
	{
		return this._key;
	}
	
	public ValueType getValue()
	{
		return this._value;
	}
	
}
