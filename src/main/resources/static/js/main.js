(function () {
  const STORAGE_KEY = 'cinelog-theme';
  const saved = localStorage.getItem(STORAGE_KEY)
    || (window.matchMedia('(prefers-color-scheme: dark)').matches ? 'dark' : 'light');

  document.documentElement.setAttribute('data-theme', saved);

  document.addEventListener('DOMContentLoaded', () => {
    const toggle = document.getElementById('themeToggle');
    if (!toggle) return;

    toggle.addEventListener('click', () => {
      const current = document.documentElement.getAttribute('data-theme');
      const next = current === 'dark' ? 'light' : 'dark';
      document.documentElement.setAttribute('data-theme', next);
      localStorage.setItem(STORAGE_KEY, next);
    });
  });
})();

document.addEventListener('DOMContentLoaded', () => {
  document.querySelectorAll('.toast.show').forEach(toast => {
    setTimeout(() => {
      toast.style.transition = 'opacity 0.4s ease, transform 0.4s ease';
      toast.style.opacity = '0';
      toast.style.transform = 'translateX(20px)';
      setTimeout(() => toast.remove(), 400);
    }, 3500);
  });
});

function filterTable() {
  const input = document.getElementById('searchInput');
  if (!input) return;

  const query = input.value.toLowerCase().trim();
  const table = document.getElementById('mainTable');
  const cards = document.querySelectorAll('.item-card');

  if (table) {
    const rows = table.querySelectorAll('tbody .table-row');
    rows.forEach(row => {
      const text = row.textContent.toLowerCase();
      const match = text.includes(query);
      row.style.transition = 'opacity 0.2s ease';
      row.style.opacity = match ? '1' : '0.15';
      row.style.pointerEvents = match ? '' : 'none';
    });
  }

  if (cards.length) {
    cards.forEach(card => {
      const text = card.textContent.toLowerCase();
      const match = text.includes(query);
      card.style.transition = 'opacity 0.2s ease, transform 0.2s ease';
      card.style.opacity = match ? '1' : '0.15';
      card.style.transform = match ? '' : 'scale(0.97)';
      card.style.pointerEvents = match ? '' : 'none';
    });
  }
}

function confirmDelete(event) {
  event.preventDefault();
  const form = event.currentTarget;

  const existing = document.getElementById('confirmModal');
  if (existing) existing.remove();

  const overlay = document.createElement('div');
  overlay.className = 'modal-overlay';
  overlay.id = 'confirmModal';

  overlay.innerHTML = `
    <div class="modal" role="dialog" aria-modal="true">
      <h3>Confirmar exclusão</h3>
      <p>Tem certeza que deseja excluir este item? Esta ação não pode ser desfeita.</p>
      <div class="modal-actions">
        <button class="btn btn-ghost" id="cancelDelete">Cancelar</button>
        <button class="btn btn-primary" id="confirmDeleteBtn" style="background:#E8342A;border-color:#E8342A">Excluir</button>
      </div>
    </div>
  `;

  document.body.appendChild(overlay);

  overlay.addEventListener('click', e => {
    if (e.target === overlay) closeModal(overlay);
  });

  document.getElementById('cancelDelete').addEventListener('click', () => closeModal(overlay));

  document.getElementById('confirmDeleteBtn').addEventListener('click', () => {
    closeModal(overlay);
    form.submit();
  });

  const onKeyDown = (e) => {
    if (e.key === 'Escape') {
      closeModal(overlay);
      document.removeEventListener('keydown', onKeyDown);
    }
  };
  document.addEventListener('keydown', onKeyDown);

  return false;
}

function closeModal(overlay) {
  overlay.style.transition = 'opacity 0.2s ease';
  overlay.style.opacity = '0';
  setTimeout(() => overlay.remove(), 200);
}

document.addEventListener('DOMContentLoaded', () => {
  const rows = document.querySelectorAll('.table-row');
  rows.forEach((row, i) => {
    row.style.opacity = '0';
    row.style.transform = 'translateY(8px)';
    setTimeout(() => {
      row.style.transition = 'opacity 0.3s ease, transform 0.3s ease';
      row.style.opacity = '1';
      row.style.transform = 'translateY(0)';
    }, 50 + i * 40);
  });

  const cards = document.querySelectorAll('.item-card');
  cards.forEach((card, i) => {
    card.style.opacity = '0';
    card.style.transform = 'translateY(12px)';
    setTimeout(() => {
      card.style.transition = 'opacity 0.35s ease, transform 0.35s ease, border-color 0.2s ease, box-shadow 0.2s ease';
      card.style.opacity = '1';
      card.style.transform = 'translateY(0)';
    }, 80 + i * 60);
  });
});
