console.log("Oui oui");

function increment() {
    let quantityDoc = document.getElementById("quantity");
    let a = Number(quantityDoc.value);
    a++;
    quantityDoc.value = a;
}

function decrement() {
    let quantityDoc = document.getElementById("quantity");
    let a = Number(quantityDoc.value);
    a--;
    a = (a < 1) ? 1 : a;
    quantityDoc.value = a;
}

function toggleFilters() {
    let filters = document.getElementById("form-filters");
    let title = document.getElementById("filter-title")

    if (filters.style.display === "none") {
        filters.style.display = "block";
        title.textContent = "Masquer les filtres↑";
    } else {
        filters.style.display = "none";
        title.textContent = "Afficher les filtres↓";
    }
}
