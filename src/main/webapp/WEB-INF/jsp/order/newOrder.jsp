<%@ include file="../common/top.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<style>
    .tab-container {
        margin: 20px 0;
    }
    .tab-buttons {
        display: flex;
        border-bottom: 2px solid #ccc;
        margin-bottom: 20px;
    }
    .tab-button {
        padding: 10px 20px;
        background-color: #f0f0f0;
        border: none;
        cursor: pointer;
        font-size: 14px;
        margin-right: 5px;
        border-top-left-radius: 5px;
        border-top-right-radius: 5px;
    }
    .tab-button.active {
        background-color: #fff;
        border-bottom: 2px solid #fff;
        margin-bottom: -2px;
        font-weight: bold;
    }
    .tab-content {
        display: none;
        padding: 20px;
        background-color: #fff;
        border: 1px solid #ddd;
        border-radius: 5px;
    }
    .tab-content.active {
        display: block;
    }
    .form-table {
        width: 100%;
    }
    .form-table td {
        padding: 8px;
    }
    .form-table input[type="text"],
    .form-table select {
        width: 100%;
        padding: 5px;
        box-sizing: border-box;
    }
    .error-message {
        color: red;
        margin: 10px 0;
        display: none;
    }
    .success-message {
        color: green;
        margin: 10px 0;
        display: none;
    }
    .order-confirm-section {
        display: none;
        margin-top: 20px;
        padding: 20px;
        background-color: #f9f9f9;
        border: 1px solid #ddd;
        border-radius: 5px;
    }
    .order-confirm-section.active {
        display: block;
    }
    .confirm-table {
        width: 100%;
        margin-bottom: 20px;
    }
    .confirm-table td {
        padding: 5px;
    }
    .button-group {
        margin-top: 20px;
        text-align: center;
    }
    .button-group button {
        padding: 10px 30px;
        margin: 0 10px;
        font-size: 16px;
        cursor: pointer;
    }
</style>

<div id="Catalog">
    <h2>New Order</h2>
    
    <div id="errorMessage" class="error-message"></div>
    <div id="successMessage" class="success-message"></div>

    <form id="orderForm">
        <div class="tab-container">
            <div class="tab-buttons">
                <button type="button" class="tab-button active" data-tab="payment">Payment Details</button>
                <button type="button" class="tab-button" data-tab="billing">Billing Address</button>
                <button type="button" class="tab-button" data-tab="shipping">Shipping Address</button>
            </div>

            <!-- Payment Details Tab -->
            <div id="tab-payment" class="tab-content active">
                <h3>Payment Details</h3>
                <table class="form-table">
                    <tr>
                        <td>Card Type:</td>
                        <td>
                            <select name="cardType" id="cardType" required>
                                <option value="">Please select</option>
                                <option value="Visa">Visa</option>
                                <option value="Paypal">Paypal</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>Card Number:</td>
                        <td>
                            <input type="text" name="creditCard" id="creditCard" required>
                            <small>* Use a fake number!</small>
                        </td>
                    </tr>
                    <tr>
                        <td>Expiry Date (MM/YYYY):</td>
                        <td><input type="text" name="expiryDate" id="expiryDate" placeholder="12/2025" required></td>
                    </tr>
                </table>
            </div>

            <!-- Billing Address Tab -->
            <div id="tab-billing" class="tab-content">
                <h3>Billing Address</h3>
                <table class="form-table">
                    <tr>
                        <td>First name:</td>
                        <td><input type="text" name="billToFirstName" id="billToFirstName" required></td>
                    </tr>
                    <tr>
                        <td>Last name:</td>
                        <td><input type="text" name="billToLastName" id="billToLastName" required></td>
                    </tr>
                    <tr>
                        <td>Address 1:</td>
                        <td><input type="text" name="billAddress1" id="billAddress1" size="40" required></td>
                    </tr>
                    <tr>
                        <td>Address 2:</td>
                        <td><input type="text" name="billAddress2" id="billAddress2" size="40"></td>
                    </tr>
                    <tr>
                        <td>City:</td>
                        <td><input type="text" name="billCity" id="billCity" required></td>
                    </tr>
                    <tr>
                        <td>State:</td>
                        <td><input type="text" name="billState" id="billState" size="4" required></td>
                    </tr>
                    <tr>
                        <td>Zip:</td>
                        <td><input type="text" name="billZip" id="billZip" size="10" required></td>
                    </tr>
                    <tr>
                        <td>Country:</td>
                        <td><input type="text" name="billCountry" id="billCountry" size="15" required></td>
                    </tr>
                </table>
            </div>

            <!-- Shipping Address Tab -->
            <div id="tab-shipping" class="tab-content">
                <h3>Shipping Address</h3>
                <table class="form-table">
                    <tr>
                        <td colspan="2">
                            <input type="checkbox" id="shippingAddressRequired" name="shippingAddressRequired">
                            <label for="shippingAddressRequired">Ship to different address...</label>
                        </td>
                    </tr>
                    <tr>
                        <td>First name:</td>
                        <td><input type="text" name="shipToFirstName" id="shipToFirstName"></td>
                    </tr>
                    <tr>
                        <td>Last name:</td>
                        <td><input type="text" name="shipToLastName" id="shipToLastName"></td>
                    </tr>
                    <tr>
                        <td>Address 1:</td>
                        <td><input type="text" name="shipAddress1" id="shipAddress1" size="40"></td>
                    </tr>
                    <tr>
                        <td>Address 2:</td>
                        <td><input type="text" name="shipAddress2" id="shipAddress2" size="40"></td>
                    </tr>
                    <tr>
                        <td>City:</td>
                        <td><input type="text" name="shipCity" id="shipCity"></td>
                    </tr>
                    <tr>
                        <td>State:</td>
                        <td><input type="text" name="shipState" id="shipState" size="4"></td>
                    </tr>
                    <tr>
                        <td>Zip:</td>
                        <td><input type="text" name="shipZip" id="shipZip" size="10"></td>
                    </tr>
                    <tr>
                        <td>Country:</td>
                        <td><input type="text" name="shipCountry" id="shipCountry" size="15"></td>
                    </tr>
                </table>
            </div>
        </div>

        <div class="button-group">
            <button type="button" id="previewOrderBtn">Preview Order</button>
        </div>
    </form>

    <!-- Order Confirmation Section -->
    <div id="orderConfirmSection" class="order-confirm-section">
        <h3>Order Confirmation</h3>
        <div id="orderPreviewContent"></div>
        <div class="button-group">
            <button type="button" id="confirmOrderBtn">Confirm</button>
            <button type="button" id="editOrderBtn">Back</button>
        </div>
    </div>
</div>

<script>
$(document).ready(function() {
    // Tab switching
    $('.tab-button').click(function() {
        var tabId = $(this).data('tab');
        
        // 移除所有活动状态
        $('.tab-button').removeClass('active');
        $('.tab-content').removeClass('active');
        
        // 添加当前选项卡的活动状态
        $(this).addClass('active');
        $('#tab-' + tabId).addClass('active');
    });

    // Preview order
    $('#previewOrderBtn').click(function() {
        // 验证当前显示的选项卡
        var activeTab = $('.tab-content.active').attr('id');
        if (activeTab === 'tab-payment') {
            if (!$('#cardType').val() || !$('#creditCard').val() || !$('#expiryDate').val()) {
                showError('Please complete payment details.');
                return;
            }
        } else if (activeTab === 'tab-billing') {
            if (!$('#billToFirstName').val() || !$('#billToLastName').val() || 
                !$('#billAddress1').val() || !$('#billCity').val() || 
                !$('#billState').val() || !$('#billZip').val() || !$('#billCountry').val()) {
                showError('Please complete billing address.');
                return;
            }
        }

        // Collect form data
        var formData = {
            cardType: $('#cardType').val(),
            creditCard: $('#creditCard').val(),
            expiryDate: $('#expiryDate').val(),
            billToFirstName: $('#billToFirstName').val(),
            billToLastName: $('#billToLastName').val(),
            billAddress1: $('#billAddress1').val(),
            billAddress2: $('#billAddress2').val(),
            billCity: $('#billCity').val(),
            billState: $('#billState').val(),
            billZip: $('#billZip').val(),
            billCountry: $('#billCountry').val(),
            shippingAddressRequired: $('#shippingAddressRequired').is(':checked'),
            shipToFirstName: $('#shipToFirstName').val(),
            shipToLastName: $('#shipToLastName').val(),
            shipAddress1: $('#shipAddress1').val(),
            shipAddress2: $('#shipAddress2').val(),
            shipCity: $('#shipCity').val(),
            shipState: $('#shipState').val(),
            shipZip: $('#shipZip').val(),
            shipCountry: $('#shipCountry').val()
        };

        // AJAX: preview order
        $.ajax({
            url: 'newOrderAjax',
            type: 'POST',
            data: JSON.stringify(formData),
            contentType: 'application/json',
            dataType: 'json',
            success: function(response) {
                if (response.success) {
                    hideError();
                    // 显示订单预览
                    displayOrderPreview(response.order);
                    $('#orderConfirmSection').addClass('active');
                    // 滚动到确认区域
                    $('html, body').animate({
                        scrollTop: $('#orderConfirmSection').offset().top
                    }, 500);
                } else {
                    showError(response.message || 'Failed to preview order.');
                }
            },
            error: function(xhr, status, error) {
                showError('Request failed: ' + error);
            }
        });
    });

    // Confirm order
    $('#confirmOrderBtn').click(function() {
        $.ajax({
            url: 'confirmOrderAjax',
            type: 'POST',
            contentType: 'application/json',
            dataType: 'json',
            success: function(response) {
                if (response.success) {
                    hideError();
                    showSuccess('Order created successfully! Order ID: ' + response.orderId);
                    $('#orderConfirmSection').removeClass('active');
                    $('#orderForm')[0].reset();
                    // Redirect after 3 seconds
                    setTimeout(function() {
                        window.location.href = 'listOrders';
                    }, 3000);
                } else {
                    showError(response.message || 'Failed to confirm order.');
                }
            },
            error: function(xhr, status, error) {
                showError('Request failed: ' + error);
            }
        });
    });

    // Back to edit
    $('#editOrderBtn').click(function() {
        $('#orderConfirmSection').removeClass('active');
        $('html, body').animate({
            scrollTop: $('.tab-container').offset().top
        }, 500);
    });

    // Render order preview
    function displayOrderPreview(order) {
        var html = '<table class="confirm-table">';
        html += '<tr><th colspan="2">Payment Details</th></tr>';
        html += '<tr><td>Card Type:</td><td>' + (order.cardType || '') + '</td></tr>';
        html += '<tr><td>Card Number:</td><td>' + (order.creditCard || '') + '</td></tr>';
        html += '<tr><td>Expiry Date:</td><td>' + (order.expiryDate || '') + '</td></tr>';
        
        html += '<tr><th colspan="2">Billing Address</th></tr>';
        html += '<tr><td>Name:</td><td>' + (order.billToFirstName || '') + ' ' + (order.billToLastName || '') + '</td></tr>';
        html += '<tr><td>Address 1:</td><td>' + (order.billAddress1 || '') + '</td></tr>';
        if (order.billAddress2) {
            html += '<tr><td>Address 2:</td><td>' + order.billAddress2 + '</td></tr>';
        }
        html += '<tr><td>City:</td><td>' + (order.billCity || '') + '</td></tr>';
        html += '<tr><td>State:</td><td>' + (order.billState || '') + '</td></tr>';
        html += '<tr><td>Zip:</td><td>' + (order.billZip || '') + '</td></tr>';
        html += '<tr><td>Country:</td><td>' + (order.billCountry || '') + '</td></tr>';
        
        html += '<tr><th colspan="2">Shipping Address</th></tr>';
        html += '<tr><td>Name:</td><td>' + (order.shipToFirstName || '') + ' ' + (order.shipToLastName || '') + '</td></tr>';
        html += '<tr><td>Address 1:</td><td>' + (order.shipAddress1 || '') + '</td></tr>';
        if (order.shipAddress2) {
            html += '<tr><td>Address 2:</td><td>' + order.shipAddress2 + '</td></tr>';
        }
        html += '<tr><td>City:</td><td>' + (order.shipCity || '') + '</td></tr>';
        html += '<tr><td>State:</td><td>' + (order.shipState || '') + '</td></tr>';
        html += '<tr><td>Zip:</td><td>' + (order.shipZip || '') + '</td></tr>';
        html += '<tr><td>Country:</td><td>' + (order.shipCountry || '') + '</td></tr>';
        
        html += '</table>';
        $('#orderPreviewContent').html(html);
    }

    // Error message
    function showError(message) {
        $('#errorMessage').text(message).show();
        $('#successMessage').hide();
    }

    // Hide error
    function hideError() {
        $('#errorMessage').hide();
    }

    // Success message
    function showSuccess(message) {
        $('#successMessage').text(message).show();
        $('#errorMessage').hide();
    }
});
</script>

<%@ include file="../common/bottom.jsp"%>