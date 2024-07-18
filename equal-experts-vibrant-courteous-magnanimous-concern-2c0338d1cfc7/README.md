# Shopping Cart API

## Overview

This API allows users to add items to their shopping cart and get details of the existing cart,
stock, product rates, and the total including tax.

## API Endpoint

- **URL**: `/cartload`
- **Method**: `POST`
- **Content-Type**: `application/json`

## Request

The request body should contain the `userId` and the `item` to be added to the cart. The `item` is
an object with product names as keys and their quantities as values.

## Pre-requisites

- The `userId` must be a valid customer who can have items in their cart or can empty their cart.

### Request Body

```json
{
  "userId": "gauti4ru",
  "item": {
    "cornflakes": 2,
    "weetabix": 1
  }
}
```

### Response Body

```json
{
  "existingCart": {
    "items": {
      "cornflakes": 2,
      "weetabix": 1
    }
  },
  "existingStock": {
    "cheerios": "100",
    "cornflakes": "298",
    "frosties": "200",
    "shreddies": "100",
    "weetabix": "199"
  },
  "productsRate": {
    "cornflakes": 2.52,
    "weetabix": 9.98
  },
  "totalIncTax": 16.9
}
```

- application.yml

-You can update the initial stock levels and tax rate in the application.yml file under the
productItems property.

```yml
productItems: "{cheerios: 100, cornflakes: 300, frosties: 200, shreddies: 100, weetabix: 200}"
taxRate: 12.5


```

## How It Works

1. The API receives a POST request with the user ID and item details.
2. It updates the user's cart with the new items.
3. It fetches the current stock levels and product rates.
4. It calculates the total price including the specified tax rate.
5. It returns the updated cart, stock levels, product rates, and the total price including tax.

## Special Cases

1. Reducing Cart Items: To reduce the quantity of an item in the cart, send a negative number for
   that
   item in the request.
2. Viewing Current Cart: To see the current state of the cart without adding or removing items, pass
   0
   as the quantity for the items.

##Example Request to View Current Cart

```json
{
    "userId": "gauti4ru",
    "item": {
        "cornflakes": 0,
        "weetabix": 0
    }
}

```

### :warning: Please read these instructions carefully and entirely first

* Clone this repository to your local machine.
* Use your IDE of choice to complete the assignment.
* When you have completed the assignment, you need to push your code to this repository
  and [mark the assignment as completed by clicking here](https://app.snapcode.review/submission_links/aca6d98a-fd6c-4fda-88a7-d6bf96d95bd7).
* Once you mark it as completed, your access to this repository will be revoked. Please make sure
  that you have
  completed the assignment and pushed all code from your local machine to this repository before you
  click the link.
* There is no time limit for this task - however, for guidance, it is expected to typically take
  around 1-2 hours.

# Begin the task

Write some code that provides the following basic shopping cart capabilities:

1. Add a product to the cart
    1. Specifying the product name and quantity
    2. Retrieve the product price by issuing a request to the [Price API](#price-api) specified
       below
    3. Cart state (totals, etc.) must be available

2. Calculate the state:
    1. Cart subtotal (sum of price for all items)
    2. Tax payable (charged at 12.5% on the subtotal)
    3. Total payable (subtotal + tax)
    4. Totals should be rounded up where required (to two decimal places)

## Price API

The price API is an existing API that returns the price details for a product, identified by it's
name. The shopping
cart should integrate with the price API to retrieve product prices.

### Price API Service Details

Base URL: `https://equalexperts.github.io/`

View Product: `GET /backend-take-home-test-data/{product}.json`

List of available products

* `cheerios`
* `cornflakes`
* `frosties`
* `shreddies`
* `weetabix`

## Example

The below is a sample with the correct values you can use to confirm your calculations

### Inputs

* Add 1 × cornflakes @ 2.52 each
* Add another 1 x cornflakes @2.52 each
* Add 1 × weetabix @ 9.98 each

### Results

* Cart contains 2 x cornflakes
* Cart contains 1 x weetabix
* Subtotal = 15.02
* Tax = 1.88
* Total = 16.90

## Tips on what we’re looking for

We value simplicity as an architectural virtue and as a development practice. Solutions should
reflect the difficulty of
the assigned task, and shouldn’t be overly complex. We prefer simple, well tested solutions over
clever solutions.

### DO

* ✅ Include unit tests.
* ✅ Test both any client and logic.
* ✅ Update the README.md with any relevant information, assumptions, and/or tradeoffs you would like
  to highlight.

### DO NOT

* ❌ Submit any form of app, such as web APIs, browser, desktop, or command-line applications.
* ❌ Add unnecessary layers of abstraction.
* ❌ Add unnecessary patterns/ architectural features that aren’t called for e.g. persistent storage.





