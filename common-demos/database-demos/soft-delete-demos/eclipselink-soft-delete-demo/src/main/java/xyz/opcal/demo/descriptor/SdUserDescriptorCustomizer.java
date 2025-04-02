package xyz.opcal.demo.descriptor;

import org.eclipse.persistence.descriptors.ClassDescriptor;
import org.eclipse.persistence.descriptors.DescriptorCustomizer;

public class SdUserDescriptorCustomizer implements DescriptorCustomizer {

	@Override
	public void customize(ClassDescriptor descriptor) throws Exception {
		descriptor.getQueryManager().setDeleteSQLString("UPDATE sd_user SET is_deleted=1 WHERE id=#id");
	}

}
