import com.cs.computerstore.Checkout;
import com.cs.computerstore.pricing.PricingRule;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;

public class CheckoutTests {


  @Test
  public void testCase1() {
    Map<String, BigDecimal> prices = new HashMap<>();
    prices.put("ipd", BigDecimal.valueOf(549.99));
    prices.put("mbp", BigDecimal.valueOf(1399.99));
    prices.put("atv", BigDecimal.valueOf(109.50));
    prices.put("vga", BigDecimal.valueOf(30.00));

    Map<String, PricingRule> pricingRules = new HashMap<>();
    pricingRules.put("atv", new PricingRule(3, BigDecimal.valueOf(219)));
    pricingRules.put("ipd", new PricingRule(5, BigDecimal.valueOf(2499.95)));

    Checkout checkout = new Checkout(prices, pricingRules);
    checkout.scan("atv");
    checkout.scan("atv");
    checkout.scan("atv");
    checkout.scan("atv");
    checkout.scan("vga");

    Assert.assertEquals(249, checkout.total().intValue());
  }

  @Test
  public void testCase2() {

    Map<String, BigDecimal> prices = new HashMap<>();
    prices.put("ipd", BigDecimal.valueOf(549.99));
    prices.put("mbp", BigDecimal.valueOf(1399.99));
    prices.put("atv", BigDecimal.valueOf(109.50));
    prices.put("vga", BigDecimal.valueOf(30.00));

    Map<String, PricingRule> pricingRules = new HashMap<>();
    pricingRules.put("atv", new PricingRule(3, BigDecimal.valueOf(219)));
    pricingRules.put("ipd", new PricingRule(5, BigDecimal.valueOf(2499.95), true));

    Checkout checkout = new Checkout(prices, pricingRules);
    checkout.scan("atv");
    checkout.scan("ipd");
    checkout.scan("ipd");
    checkout.scan("atv");
    checkout.scan("ipd");
    checkout.scan("ipd");
    checkout.scan("ipd");

    Assert.assertEquals(0,checkout.total().compareTo(new BigDecimal("2718.95")));
  }
}