// Configurazione API
const API_BASE_URL = "http://localhost:3001";

// Variabili globali
let currentPage = 0;
let pageSize = 10;
let totalPages = 0;

// Elementi DOM
const fattureTable = document.getElementById("fatture-tbody");
const prevBtn = document.getElementById("prev-btn");
const nextBtn = document.getElementById("next-btn");
const pageInfo = document.getElementById("page-info");

// Event listeners
document.addEventListener("DOMContentLoaded", init);

async function init() {
  checkAuth();
  loadInvoices();
}

// Controllo autenticazione
function checkAuth() {
  const token = localStorage.getItem("token");
  if (!token) {
    window.location.href = "index.html";
    return;
  }
}

// Logout
function handleLogout() {
  localStorage.clear();
  window.location.href = "index.html";
}

// Caricamento fatture
async function loadInvoices() {
  try {
    const token = localStorage.getItem("token");
    const urlParams = new URLSearchParams(window.location.search);
    const clientId = urlParams.get("clientId");

    let url = `${API_BASE_URL}/api/fatture?page=${currentPage}&size=${pageSize}`;
    if (clientId) {
      url += `&clienteId=${clientId}`;
    }

    const response = await fetch(url, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });

    if (response.ok) {
      const data = await response.json();
      displayInvoices(data.content || []);
      updatePagination(data);
    } else {
      displayInvoices([]);
    }
  } catch (error) {
    console.error("Errore caricamento fatture:", error);
    displayInvoices([]);
  }
}

// Visualizzazione fatture
function displayInvoices(fatture) {
  if (!fattureTable) return;

  if (fatture.length === 0) {
    fattureTable.innerHTML = `
      <tr>
        <td colspan="5" style="text-align: center; padding: 2rem; color: #666;">
          Nessuna fattura trovata
        </td>
      </tr>`;
    return;
  }

  fattureTable.innerHTML = fatture
    .map(
      (fattura) => `
    <tr>
      <td>${fattura.numero || "-"}</td>
      <td>${fattura.cliente ? fattura.cliente.ragioneSociale : "-"}</td>
      <td>${formatDate(fattura.data)}</td>
      <td>€${fattura.importo ? fattura.importo.toLocaleString() : "0"}</td>
      <td>
        <span class="status-badge ${getStatusClass(fattura.stato)}">
          ${fattura.stato || "N/A"}
        </span>
      </td>
    </tr>
  `
    )
    .join("");
}

// Formattazione data
function formatDate(dateString) {
  if (!dateString) return "-";
  const date = new Date(dateString);
  return date.toLocaleDateString("it-IT");
}

function getStatusClass(stato) {
  if (!stato) return "";
  return stato.toLowerCase().replace(/\s+/g, "-");
}

function updatePagination(data) {
  totalPages = data.totalPages || 0;
  const pageInfoElement = document.getElementById("page-info");

  if (pageInfoElement) {
    pageInfoElement.textContent = `Pagina ${currentPage + 1} di ${totalPages}`;
  }

  const prevBtn = document.getElementById("prev-btn");
  const nextBtn = document.getElementById("next-btn");

  if (prevBtn) prevBtn.disabled = currentPage === 0;
  if (nextBtn) nextBtn.disabled = currentPage >= totalPages - 1;
}

function previousPage() {
  if (currentPage > 0) {
    currentPage--;
    loadInvoices();
  }
}

function nextPage() {
  if (currentPage < totalPages - 1) {
    currentPage++;
    loadInvoices();
  }
}

// Event listeners
document.addEventListener("DOMContentLoaded", () => {
  const prevBtn = document.getElementById("prev-btn");
  const nextBtn = document.getElementById("next-btn");
  const logoutBtn = document.getElementById("logout-btn");

  if (prevBtn) prevBtn.addEventListener("click", previousPage);
  if (nextBtn) nextBtn.addEventListener("click", nextPage);
  if (logoutBtn) logoutBtn.addEventListener("click", handleLogout);
});
