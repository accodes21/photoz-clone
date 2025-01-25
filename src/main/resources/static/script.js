async function fetchPhotos() {
  try {
    const response = await fetch("/photoz/api");
    if (!response.ok) throw new Error("Failed to fetch photos");

    const photos = await response.json();
    const gallery = document.getElementById("gallery");
    gallery.innerHTML = "";

    photos.forEach((photo) => {
      const photoElement = document.createElement("div");
      photoElement.classList.add("gallery-item");
      photoElement.innerHTML = `
                        <img src="/download/${photo.id}" alt="${photo.fileName}" />
                        <p>${photo.fileName}</p>
                        <button onclick="deletePhoto(${photo.id})">Delete</button>
                        <button onclick="openEditDialog(${photo.id})">Edit</button>
                    `;
      gallery.appendChild(photoElement);
    });
  } catch (error) {
    console.error(error);
    document.getElementById("gallery").innerHTML =
      "<p>Failed to load photos. Please try again later.</p>";
  }
}

async function deletePhoto(id) {
  if (!confirm("Are you sure you want to delete this photo?")) return;

  try {
    const response = await fetch(`/photoz/api/${id}`, { method: "DELETE" });
    if (!response.ok) throw new Error(`Failed to delete photo with ID ${id}`);

    alert("Photo deleted successfully");
    fetchPhotos();
  } catch (error) {
    console.error(error);
    alert(`Error deleting photo: ${error.message}`);
  }
}

fetchPhotos();

let currentPhotoId = null;

function openEditDialog(id) {
  currentPhotoId = id;
  document.getElementById("overlay").style.display = "block";
  document.getElementById("edit-dialog").style.display = "block";
}

function closeEditDialog() {
  document.getElementById("overlay").style.display = "none";
  document.getElementById("edit-dialog").style.display = "none";
  currentPhotoId = null;
}

async function editPhoto() {
  const fileInput = document.getElementById("edit-file");
  const file = fileInput.files[0];
  if (!file) {
    alert("Please select a file to upload.");
    return;
  }

  const formData = new FormData();
  formData.append("data", file);

  try {
    const response = await fetch(`/photoz/api/${currentPhotoId}`, {
      method: "PUT",
      body: formData,
    });
    if (!response.ok) throw new Error("Failed to update photo");

    alert("Photo updated successfully");
    closeEditDialog();
    fetchPhotos();
  } catch (error) {
    console.error(error);
    alert("Error updating photo: " + error.message);
  }
}
