<%@include file="../common/top.jsp"%>

<div id="Welcome">
    <div id="WelcomeContent">
        <!--显示登陆用户的firstName-->
    </div>
</div>

<div id="Main">
    <div id="Sidebar">
        <div id="SidebarContent">
            <a href="categoryForm?categoryId=FISH"><img src="images/fish_icon.gif" /></a><br />
            SaltWater, Freshwater <br />
            <a href="categoryForm?categoryId=DOGS"><img src="images/dogs_icon.gif" /></a><br />
            Various Breeds <br />
            <a href="categoryForm?categoryId=CATS"><img src="images/cats_icon.gif" /></a><br />
            Various Breeds, Exotic Varieties <br />
            <a href="categoryForm?categoryId=REPTILES"><img src="images/reptiles_icon.gif" /></a><br />
            Lizards, Turtles, Snakes <br />
            <a href="categoryForm?categoryId=BIRDS"><img src="images/birds_icon.gif" /></a><br />
            Exotic Varieties
        </div>
    </div>

    <div id="MainImage">
        <div id="MainImageContent">
            <map name="estoremap">
                <area alt="Birds" coords="72,2,280,250"
                      href="categoryForm?categoryId=BIRDS" shape="RECT" data-category-id="BIRDS" />
                <area alt="Fish" coords="2,180,72,250"
                      href="categoryForm?categoryId=FISH" shape="RECT" data-category-id="FISH" />
                <area alt="Dogs" coords="60,250,130,320"
                      href="categoryForm?categoryId=DOGS" shape="RECT" data-category-id="DOGS" />
                <area alt="Reptiles" coords="140,270,210,340"
                      href="categoryForm?categoryId=REPTILES" shape="RECT" data-category-id="REPTILES" />
                <area alt="Cats" coords="225,240,295,310"
                      href="categoryForm?categoryId=CATS" shape="RECT" data-category-id="CATS" />
                <area alt="Birds" coords="280,180,350,250"
                      href="categoryForm?categoryId=BIRDS" shape="RECT" data-category-id="BIRDS" />
            </map>
            <img height="355" src="images/splash.gif" align="middle"
                 usemap="#estoremap" width="350" />

            <div id="categoryHoverCard" style="display:none; position:fixed; left:0; top:0; width:320px; z-index:2147483647; background:#111; color:#fff; border:1px solid #333; border-radius:8px; padding:10px 12px; box-shadow:0 10px 30px rgba(0,0,0,0.35); font-family: Arial, Helvetica, sans-serif; font-size:12px; line-height:1.4; pointer-events:auto;">
                <div id="categoryHoverCardTitle" style="font-weight:700; font-size:13px; margin-bottom:6px;">Loading...</div>
                <div id="categoryHoverCardBody"></div>
            </div>

            <script>
                (function () {
                    var card = document.getElementById('categoryHoverCard');
                    var cardTitle = document.getElementById('categoryHoverCardTitle');
                    var cardBody = document.getElementById('categoryHoverCardBody');
                    var map = document.querySelector('map[name="estoremap"]');

                    if (!card || !cardTitle || !cardBody || !map) {
                        return;
                    }

                    var hideTimer = null;
                    var hoverCategoryId = null;
                    var cache = {};

                    function setCardVisible(visible) {
                        card.style.display = visible ? 'block' : 'none';
                    }

                    function safeText(s) {
                        return (s === null || s === undefined) ? '' : String(s);
                    }

                    function renderError(message) {
                        cardTitle.textContent = 'Items';
                        cardBody.textContent = message || 'Failed to load.';
                    }

                    function renderItems(categoryId, items) {
                        cardTitle.textContent = 'Items';

                        if (!items || !items.length) {
                            cardBody.textContent = 'No items available in this category.';
                            return;
                        }

                        var html = '';
                        html += '<div style="opacity:0.85; margin-bottom:6px;">Category ID: ' + escapeHtml(categoryId) + '</div>';
                        html += '<div>';
                        for (var i = 0; i < items.length; i++) {
                            var it = items[i];
                            if (!it || !it.itemId) {
                                continue;
                            }
                            var href = 'itemForm?itemId=' + encodeURIComponent(it.itemId);
                            var text = (it.displayName && safeText(it.displayName).trim()) ? safeText(it.displayName) : safeText(it.itemId);
                            html += '<div style="margin:4px 0;">'
                                + '<a href="' + href + '" style="color:#9ad; text-decoration:none;">'
                                + escapeHtml(text)
                                + '</a>'
                                + '</div>';
                        }
                        html += '</div>';
                        cardBody.innerHTML = html;
                    }

                    function escapeHtml(str) {
                        return safeText(str)
                            .replace(/&/g, '&amp;')
                            .replace(/</g, '&lt;')
                            .replace(/>/g, '&gt;')
                            .replace(/"/g, '&quot;')
                            .replace(/'/g, '&#39;');
                    }

                    function positionCard(clientX, clientY) {
                        var padding = 12;
                        var offsetX = 14;
                        var offsetY = 14;

                        var desiredLeft = clientX + offsetX;
                        var desiredTop = clientY + offsetY;

                        var vw = window.innerWidth || document.documentElement.clientWidth;
                        var vh = window.innerHeight || document.documentElement.clientHeight;

                        card.style.left = '0px';
                        card.style.top = '0px';

                        var rect = card.getBoundingClientRect();
                        var w = rect.width || 280;
                        var h = rect.height || 60;

                        var left = desiredLeft;
                        var top = desiredTop;

                        if (left + w + padding > vw) {
                            left = Math.max(padding, vw - w - padding);
                        }
                        if (top + h + padding > vh) {
                            top = Math.max(padding, vh - h - padding);
                        }

                        card.style.left = left + 'px';
                        card.style.top = top + 'px';
                    }

                    function fetchCategoryItems(categoryId) {
                        if (cache[categoryId]) {
                            return Promise.resolve(cache[categoryId]);
                        }
                        var url = 'categoryItemsAjax?categoryId=' + encodeURIComponent(categoryId) + '&limit=20';
                        return fetch(url, { method: 'GET' })
                            .then(function (r) {
                                if (!r.ok) {
                                    throw new Error('HTTP ' + r.status);
                                }
                                return r.json();
                            })
                            .then(function (data) {
                                if (!data || data.ok !== true || !Array.isArray(data.items)) {
                                    throw new Error('Invalid response');
                                }
                                cache[categoryId] = data.items;
                                return data.items;
                            });
                    }

                    function scheduleHide() {
                        if (hideTimer) {
                            clearTimeout(hideTimer);
                        }
                        hideTimer = setTimeout(function () {
                            hoverCategoryId = null;
                            setCardVisible(false);
                        }, 80);
                    }

                    map.addEventListener('mouseover', function (e) {
                        var t = e.target;
                        if (!t || t.tagName !== 'AREA') {
                            return;
                        }
                        var categoryId = t.getAttribute('data-category-id');
                        if (!categoryId) {
                            return;
                        }

                        if (hideTimer) {
                            clearTimeout(hideTimer);
                            hideTimer = null;
                        }

                        hoverCategoryId = categoryId;
                        cardTitle.textContent = 'Loading...';
                        cardBody.textContent = '';
                        setCardVisible(true);
                        positionCard(e.clientX, e.clientY);

                        fetchCategoryItems(categoryId)
                            .then(function (items) {
                                if (hoverCategoryId !== categoryId) {
                                    return;
                                }
                                renderItems(categoryId, items);
                                positionCard(e.clientX, e.clientY);
                            })
                            .catch(function () {
                                if (hoverCategoryId !== categoryId) {
                                    return;
                                }
                                renderError('Unable to load item list.');
                                positionCard(e.clientX, e.clientY);
                            });
                    });

                    map.addEventListener('mousemove', function (e) {
                        if (!hoverCategoryId) {
                            return;
                        }
                        positionCard(e.clientX, e.clientY);
                    });

                    map.addEventListener('mouseout', function (e) {
                        var t = e.target;
                        if (!t || t.tagName !== 'AREA') {
                            return;
                        }
                        scheduleHide();
                    });

                    card.addEventListener('mouseenter', function () {
                        if (hideTimer) {
                            clearTimeout(hideTimer);
                            hideTimer = null;
                        }
                    });

                    card.addEventListener('mouseleave', function () {
                        scheduleHide();
                    });
                })();
            </script>

            </div>
    </div>

    <div id="Separator">&nbsp;</div>
</div>

<%@include file="../common/bottom.jsp"%>
