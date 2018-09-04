package mastercard;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestVendorbestPrices {

	@Test
	void test() {
		VendorBestPrices vendorBestPrices = new VendorBestPrices();
		String bestPrices = vendorBestPrices.getBestVendorPrices();
		System.out.println(bestPrices);
		//fail("Not yet implemented");
		assertTrue(bestPrices.length() > 0);
	}

}
