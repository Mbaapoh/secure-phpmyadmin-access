# Microcks Stateful API Mock Testing

This directory contains the OpenAPI specifications for our stateful mock endpoints deployed on Microcks. The pipeline is fully automated—any changes pushed to the `main` or `feature/stateful-mocking` branches will automatically trigger a Jenkins build that synchronizes these mocks directly to Microcks via a GitHub Webhook.

Below are the `curl` commands you can use to test the state machine logic locally against the deployed `microcks.demo.okay.cm` instance.

---

## 1. Cart API Tests

The Cart API demonstrates standard Header-driven state isolation. It uses the `customerId` header to separate carts between different testers.

### Step A: Initialize / View Cart
Send a `GET` request to see the current state of your isolated cart.
```bash
curl -k -s -H "customerId: mock-tester" \
  "https://microcks.demo.okay.cm/rest/Cart+API/1.0.0/cart" | jq
```

### Step B: Add an Item to the Cart
Send a `PUT` request to add a product. The Groovy script running in Microcks will dynamically add this item to the array in the persistent datastore.
```bash
curl -i -k -X PUT \
  -H "customerId: mock-tester" \
  -H "Content-Type: application/json" \
  -d '{"productId": "Millefeuille", "quantity": 2, "price": 4.0}' \
  "https://microcks.demo.okay.cm/rest/Cart+API/1.0.0/cart/items"
```

### Step C: Verify Mutated State
Run the `GET` request again. You will see the item has been added, and the `totalPrice` has been dynamically calculated (e.g., `8.0`).
```bash
curl -k -s -H "customerId: mock-tester" \
  "https://microcks.demo.okay.cm/rest/Cart+API/1.0.0/cart" | jq
```

---

## 2. MTN MoMo Collections API Tests

The MTN MoMo API demonstrates asynchronous state transitions. The mock starts in a `PENDING` state and automatically transitions to `SUCCESSFUL` after you poll it 3 times.

### Step A: Initialize the Payment
Send a `POST` request to initialize the payment flow. **Important**: You must generate a unique `X-Reference-Id` (UUID) for every new payment test you run.
```bash
curl -i -k -X POST "https://microcks.demo.okay.cm/rest/MTN+MoMo+Collections+API/v1/collection/v1_0/requesttopay" \
  -H "X-Reference-Id: 98765432-abcd-efgh-1234-999999999999" \
  -H "X-Target-Environment: sandbox" \
  -H "Ocp-Apim-Subscription-Key: dummy-key-123" \
  -H "Content-Type: application/json" \
  -d '{"amount":"8000","currency":"XAF","externalId":"INV-WEBHOOK","payer":{"partyIdType":"MSISDN","partyId":"237671111222"}}'
```
*Expected Output: `HTTP 202 Accepted`*

### Step B: Poll the Status (Transitions)
To test your frontend or backend's polling mechanism, run the following `GET` command repeatedly. 

**First & Second Poll**: Returns `status: PENDING`
**Third Poll**: The Groovy state machine increments past the threshold and returns `status: SUCCESSFUL` along with a generated `financialTransactionId`.

```bash
# Run this multiple times to watch the state transition!
curl -k -s \
  -H "X-Reference-Id: 98765432-abcd-efgh-1234-999999999999" \
  -H "X-Target-Environment: sandbox" \
  -H "Ocp-Apim-Subscription-Key: dummy-key-123" \
  "https://microcks.demo.okay.cm/rest/MTN+MoMo+Collections+API/v1/collection/v1_0/requesttopay" | jq
```
