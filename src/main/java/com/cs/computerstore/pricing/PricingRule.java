package com.cs.computerstore.pricing;

import java.math.BigDecimal;

/**
 * Class holding all the pricing rules
 */
public class PricingRule {


  /**
   * Quantity for which the pricing rule applies.
   * For example, if you buy 3 Apple TVs, you will pay the price of 2 only that means the quantity = 3
   */
  private final int quantity;
  /**
   * Total price for the given quantity.
   * For example, if you buy 3 Apple TVs, you will pay the price of 2 only.
   * Cost of each Apple TV = $109.50.
   * ${priceForQuantity} = 109.50 * 2 = 219
   */
  private final BigDecimal priceForQuantity;
  /**
   * Should drop the price of each item.
   * For example
   * the brand new Super iPad will have a bulk discounted applied, where the price will drop to $499.99 each,
   * so for Super iPad ${dropPriceForEachItem} = true
   */
  private final boolean dropPriceForEachItem;

  /**
   *
   * @param quantity Quantity for which the pricing rule applies.
   * @param priceForQuantity Total price for the given quantity.
   */
  public PricingRule(int quantity, BigDecimal priceForQuantity) {
    this(quantity, priceForQuantity, false);
  }

  /**
   *
   * @param quantity Quantity for which the pricing rule applies.
   * @param priceForQuantity Total price for the given quantity.
   * @param dropPriceForEachItem Should drop the price of each item if the user buys more than x quantity
   */
  public PricingRule(final int quantity, final BigDecimal priceForQuantity, final boolean dropPriceForEachItem) {
    this.quantity = quantity;
    this.priceForQuantity = priceForQuantity;
    this.dropPriceForEachItem = dropPriceForEachItem;
  }

  /**
   * Get the quantity
   * @return
   */
  public int getQuantity() {
    return quantity;
  }

  /**
   * Get price for each quantity
   * @param numQuantities
   * @return
   */
  public BigDecimal getPriceForQuantity(int numQuantities) {
    return priceForQuantity.multiply(BigDecimal.valueOf(numQuantities));
  }

  /**
   *
   * @return
   */
  public boolean isDropPriceForEachItem() {
    return dropPriceForEachItem;
  }

  /**
   *
   * @return
   */
  public BigDecimal getPriceAfterDiscount() {
    return priceForQuantity.divide(new BigDecimal(quantity));
  }
}