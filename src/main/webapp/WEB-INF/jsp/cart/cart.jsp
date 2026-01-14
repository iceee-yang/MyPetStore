<%@ include file="../common/top.jsp"%>

<div id="BackLink">
  <a href="mainForm">Return to Main Menu</a>
</div>

<div id="Catalog">

  <div id="Cart">

    <h2>Shopping Cart</h2>

    <!-- 提交到 updateCart，由 UpdateCartServlet 的 doPost 处理 -->
    <form action="updateCart" method="post" id="cartForm">
      <table>
        <tr>
          <th><b>Item ID</b></th>
          <th><b>Product ID</b></th>
          <th><b>Description</b></th>
          <th><b>In Stock?</b></th>
          <th><b>Quantity</b></th>
          <th><b>List Price</b></th>
          <th><b>Total Cost</b></th>
          <th>&nbsp;</th>
        </tr>

        <c:if test="${sessionScope.cart.numberOfItems == 0}">
          <tr>
            <td colspan="8"><b>Your cart is empty.</b></td>
          </tr>
        </c:if>

        <c:forEach var="cartItem" items="${sessionScope.cart.cartItems}">
          <tr>
            <td>
              <a href="itemForm?itemId=${cartItem.item.itemId}">${cartItem.item.itemId}</a>
            </td>
            <td>${cartItem.item.product.productId}</td>
            <td>${cartItem.item.attribute1} ${cartItem.item.attribute2}
                ${cartItem.item.attribute3} ${cartItem.item.attribute4}
                ${cartItem.item.attribute5} ${cartItem.item.product.name}</td>
            <td>${cartItem.inStock}</td>
            <td>
              <input type="number" min="0" name="${cartItem.item.itemId}" value="${cartItem.quantity}" 
                     data-item-id="${cartItem.item.itemId}"
                     onchange="updateCartItemQuantity(this, '${cartItem.item.itemId}')"
                     style="width: 50px;">
            </td>
            <td><fmt:formatNumber value="${cartItem.item.listPrice}"
                                  pattern="$#,##0.00" /></td>
            <td><fmt:formatNumber value="${cartItem.total}"
                                  pattern="$#,##0.00" /></td>
            <td>
              <a href="removeItemFromCart?workingItemId=${cartItem.item.itemId}" class="Button">Remove</a>
            </td>
          </tr>
        </c:forEach>
        <tr>
          <td colspan="7">
            Sub Total: $<span id="cartSubTotal"><fmt:formatNumber value="${sessionScope.cart.subTotal}" pattern="#0.00" /></span>
            <input type="submit" value="Update Cart" id="updateCartSubmit">
          </td>
          <td>&nbsp;</td>
        </tr>
      </table>

    </form>


    <c:if test="${sessionScope.cart.numberOfItems > 0}">
      <a href="newOrderForm" class="Button">Proceed to Checkout</a>
    </c:if>
  </div>

  <div id="MyList">
    <c:if test="${sessionScope.loginAccount != null}">
        <c:if test="${!empty sessionScope.loginAccount.listOption}">
          <%@ include file="includeMyList.jsp"%>
        </c:if>
    </c:if>
  </div>

  <div id="Separator">&nbsp;</div>
</div>

<script>
function updateCartItemQuantity(input, itemId) {
    const quantity = input.value;

    const originalValue = input.value;
    input.disabled = true;

    fetch('updateCartItemAjax', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: 'itemId=' + encodeURIComponent(itemId) + '&quantity=' + encodeURIComponent(quantity)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return response.json();
    })
    .then(data => {
        if (data.ok) {
            document.getElementById('cartSubTotal').textContent = data.subTotal;
            const cartNumEl = document.getElementById('cartNumberOfItems');
            if (cartNumEl) {
                cartNumEl.textContent = data.numberOfItems;
            }

            const row = input.closest('tr');
            const totalCell = row ? row.querySelector('td:nth-child(7)') : null;
            if (totalCell) {
                totalCell.textContent = '$' + data.itemTotal;
            }

            if (data.quantity <= 0 && row) {
                row.remove();
            }
        } else {
            alert('更新购物车失败: ' + (data.message || '未知错误'));
            input.value = originalValue;
        }
    })
    .catch(error => {
        console.error('Error:', error);
        alert('更新购物车时出错，请重试');
        input.value = originalValue;
    })
    .finally(() => {
        input.disabled = false;
    });
}
</script>

<%@ include file="../common/bottom.jsp"%>