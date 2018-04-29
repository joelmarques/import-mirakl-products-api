package com.mirakl.product.api.converter;

public interface Converter<Source, Target> {
	Target convert(Source source);
}