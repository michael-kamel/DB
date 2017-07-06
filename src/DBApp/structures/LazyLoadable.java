package DBApp.structures;

import java.util.Optional;

public abstract class LazyLoadable<O>
{
	private Optional<O> original;
	
	protected abstract O load();
	
	public O getOriginal()
	{
		return original.orElseGet(this::loadAndGetOriginal);
	}
	protected void setOriginal(O orig) 
	{
		this.original = Optional.of(orig);
	}
	protected void init()
	{
		original = Optional.empty();
	}
	protected O loadAndGetOriginal()
	{
		setOriginal(load());
		return original.get();
	}
}
