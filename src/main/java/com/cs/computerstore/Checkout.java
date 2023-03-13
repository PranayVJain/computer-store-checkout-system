/*
 * #%L
 * computer-store-checkout-system
 *
 * %%
 * Copyright (C) 2018 - 2022 Nuance Communications Inc. All Rights Reserved.
 * %%
 *
 * The copyright to the computer program(s) herein is the property of
 * Nuance Communications Inc. The program(s) may be used and/or copied
 * only with the written permission from Nuance Communications Inc.
 * or in accordance with the terms and conditions stipulated in the
 * agreement/contract under which the program(s) have been supplied.
 * #L%
 */
package com.cs.computerstore;

import com.cs.computerstore.pricing.PricingRule;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Checkout {

  private Map<String, BigDecimal> prices;
  private Map<String, PricingRule> pricingRules;
  private List<String> scannedItems;

  /**
   *
   * @param prices
   * @param pricingRules
   */
  public Checkout(final Map<String, BigDecimal> prices, final Map<String, PricingRule> pricingRules) {
    this.prices = prices;
    this.pricingRules = pricingRules;
    this.scannedItems = new ArrayList<>();
  }

  public void scan(String item) {
    scannedItems.add(item);
  }

  /**
   * Returns the total
   * @return
   */
  public BigDecimal total() {
    BigDecimal total = BigDecimal.ZERO;

    // Count the number of items for each SKU
    Map<String, Integer> itemCounts = new HashMap<>();
    for (int i = 0; i < scannedItems.size(); i++) {
      itemCounts.put(scannedItems.get(i), itemCounts.getOrDefault(scannedItems.get(i), 0) + 1);
    }

    // Calculate the total price for each item.
    for (Map.Entry<String, Integer> entry : itemCounts.entrySet()) {
      String item = entry.getKey();
      int count = entry.getValue();
      // Get the pricing rule.
      PricingRule pricingRule = pricingRules.get(item);
      BigDecimal itemPrice = prices.get(item);
      // Check if the pricing rule is present and the count of items exceeds the quantity required to apply the rule.
      if (pricingRule != null && count >= pricingRule.getQuantity()) {
        int numDiscounts = count / pricingRule.getQuantity();
        int remainingItems = count % pricingRule.getQuantity();
        // check if the item price needs to be dropped.
        itemPrice = pricingRule.isDropPriceForEachItem() ? pricingRule.getPriceAfterDiscount() : itemPrice;
        total = total
            .add(pricingRule.getPriceForQuantity(numDiscounts).add(itemPrice.multiply(BigDecimal.valueOf(remainingItems))));
      } else {
        // If no pricing rules are present use the amount as it is.
        total = total.add(itemPrice.multiply(BigDecimal.valueOf(count)));
      }
    }
    return total;
  }
}
