<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>

<html>

<head>
    <meta charset="UTF-8" />
    <title>MyPetStore</title>
    <link rel="StyleSheet" href="css/mypetstore.css" type="text/css" media="screen" />
</head>

<body>

<div id="Header">

    <div id="Logo">
        <div id="LogoContent">
            <a href="mainForm"><img src="images/logo-topbar.gif" /></a>
        </div>
    </div>

    <div id="Menu">
        <div id="MenuContent"><a href="viewCart"><img align="middle" name="img_cart" src="images/cart.gif" /></a>
            <img align="middle" src="images/separator.gif" />
            <c:if test="${sessionScope.loginAccount == null}">
                <a href="signonForm">Sign In</a>
                <img align="middle" src="images/separator.gif" />
            </c:if>

            <c:if test="${sessionScope.loginAccount != null}">
                <a href="signOut">Sign Out</a>
                <img align="middle" src="images/separator.gif" />
                <a href="editAccountForm">My Account</a>
                <img align="middle" src="images/separator.gif" />
                <a href="listLogs">My Logs</a>
                <img align="middle" src="images/separator.gif" />
            </c:if>
            <a href="../help.html">?</a>
        </div>
    </div>

    <div id="Search">
        <div id="SearchContent">
            <form action="searchCategory" method="get" id="globalSearchForm">
                <input type="text" name="keyword" size="14" id="globalSearchInput" list="productSuggestions" autocomplete="off">
                <datalist id="productSuggestions"></datalist>
                <input type="submit" value="Search">
            </form>
            <script>
                (function () {
                    var input = document.getElementById('globalSearchInput');
                    var form = document.getElementById('globalSearchForm');
                    var datalist = document.getElementById('productSuggestions');

                    if (!input || !form || !datalist) {
                        return;
                    }

                    var debounceTimer = null;
                    var latestProducts = [];

                    function clearOptions() {
                        while (datalist.firstChild) {
                            datalist.removeChild(datalist.firstChild);
                        }
                    }

                    function fillOptions(products) {
                        clearOptions();
                        for (var i = 0; i < products.length; i++) {
                            var p = products[i];
                            if (!p || !p.label) {
                                continue;
                            }
                            var opt = document.createElement('option');
                            opt.value = p.label;
                            datalist.appendChild(opt);
                        }
                    }

                    function fetchSuggestions(q) {
                        var url = 'autocompleteProduct?q=' + encodeURIComponent(q);
                        fetch(url, { method: 'GET' })
                            .then(function (r) { return r.json(); })
                            .then(function (data) {
                                latestProducts = Array.isArray(data) ? data : [];
                                fillOptions(latestProducts);
                            })
                            .catch(function () {
                                latestProducts = [];
                                clearOptions();
                            });
                    }

                    input.addEventListener('input', function () {
                        var q = (input.value || '').trim();
                        if (q.length < 2) {
                            latestProducts = [];
                            clearOptions();
                            return;
                        }
                        if (debounceTimer) {
                            clearTimeout(debounceTimer);
                        }
                        debounceTimer = setTimeout(function () {
                            fetchSuggestions(q);
                        }, 200);
                    });

                    form.addEventListener('submit', function (e) {
                        var v = (input.value || '').trim();
                        for (var i = 0; i < latestProducts.length; i++) {
                            var p = latestProducts[i];
                            if (!p) {
                                continue;
                            }
                            if (v === p.label) {
                                e.preventDefault();
                                window.location.href = 'productForm?productId=' + encodeURIComponent(p.productId);
                                return;
                            }
                        }
                    });
                })();
            </script>
        </div>
    </div>

    <div id="QuickLinks">
        <a href="categoryForm?categoryId=FISH"><img src="images/sm_fish.gif" /></a>
        <img src="images/separator.gif" />
        <a href="categoryForm?categoryId=DOGS"><img src="images/sm_dogs.gif" /></a>
        <img src="images/separator.gif" />
        <a href="categoryForm?categoryId=REPTILES"><img src="images/sm_reptiles.gif" /></a>
        <img src="images/separator.gif" />
        <a href="categoryForm?categoryId=CATS"><img src="images/sm_cats.gif" /></a>
        <img src="images/separator.gif" />
        <a href="categoryForm?categoryId=BIRDS"><img src="images/sm_birds.gif" /></a>
    </div>

    </div>

<div id="Content">