<%@ include file="../common/top.jsp"%>

<div id="Catalog">

  <form action="newOrder" method="post">
    <table>
      <tr>
        <th colspan=2>Payment Details</th>
      </tr>
      <tr>
        <td>Card Type:</td>
        <td>
          <select name="order.cardType">
              <option value="Visa">Visa</option>
              <option value="Paypal">Paypal</option>
          </select>
        </td>
      </tr>
      <tr>
        <td>Card Number:</td>
        <td>
          <input type="text" name="order.creditCard"> * Use a fake number!
        </td>
      </tr>
      <tr>
        <td>Expiry Date (MM/YYYY):</td>
        <td><input type="text" name="order.expiryDate"></td>
      </tr>
      <tr>
        <th colspan=2>Billing Address</th>
      </tr>

      <tr>
        <td>First name:</td>
        <td><input type="text" name="order.billToFirstName"></td>
      </tr>
      <tr>
        <td>Last name:</td>
        <td><input type="text" name="order.billToLastName"></td>
      </tr>
      <tr>
        <td>Address 1:</td>
        <td><input type="text" name="order.billAddress1" size="40"></td>
      </tr>
      <tr>
        <td>Address 2:</td>
        <td><input type="text" name="order.billAddress2" size="40"></td>
      </tr>
      <tr>
        <td>City:</td>
        <td><input type="text" name="order.billCity"></td>
      </tr>
      <tr>
        <td>State:</td>
        <td><input type="text" name="order.billState" size="4"></td>
      </tr>
      <tr>
        <td>Zip:</td>
        <td><input type="text" name="order.billZip" size="10"></td>
      </tr>
      <tr>
        <td>Country:</td>
        <td><input type="text" name="order.billCountry" size="15"></td>
      </tr>

      <tr>
        <td colspan=2>
          <input type="checkbox" name="shippingAddressRequired">Ship to different address...
        </td>
      </tr>

    </table>

    <input type="submit" name="newOrder" value="Continue">

  </form>
</div>

<%@ include file="../common/bottom.jsp"%>