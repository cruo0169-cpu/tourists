(function () {
  // 高亮当前导航
  var path = location.pathname;
  document.querySelectorAll('.nav-inner a').forEach(function (a) {
    var href = a.getAttribute('href');
    if (!href) return;
    if (href === '/home' && path === '/home') { a.classList.add('active'); }
    else if (href !== '/home' && path.startsWith(href)) { a.classList.add('active'); }
  });

  // 危险操作确认
  document.querySelectorAll('form.js-confirm').forEach(function (form) {
    form.addEventListener('submit', function (e) {
      var msg = form.getAttribute('data-confirm') || '确定执行该操作吗？';
      if (!window.confirm(msg)) e.preventDefault();
    });
  });

  // 提示条自动消失
  document.querySelectorAll('.alert').forEach(function (el) {
    if (el.classList.contains('ok')) {
      setTimeout(function () { el.style.transition = 'opacity .5s'; el.style.opacity = '0'; }, 3500);
    }
  });
})();
