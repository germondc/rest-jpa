package test.clyde.restjpa.converters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import test.clyde.restjpa.entities.StudentType;

@Converter(autoApply = true)
public class StudentTypeConverter implements AttributeConverter<StudentType, String> {

	@Override
	public String convertToDatabaseColumn(StudentType attribute) {
		if (attribute == null)
			return null;
		return attribute.getCode();
	}

	@Override
	public StudentType convertToEntityAttribute(String dbData) {
		if (dbData == null)
			return null;
		
		return StudentType.fromCode(dbData);
	}

}
