function closeOpenDropdowns(e) {
	let openDropdownEls = document.querySelectorAll("details.dropdown[open]");

	if (openDropdownEls.length > 0) {
		if (e.target.parentElement.nodeName.toUpperCase() !== "SUMMARY") {
			openDropdownEls.forEach((dropdown) => {
				dropdown.removeAttribute("open");
			});
		}
	}
}

document.addEventListener("click", closeOpenDropdowns);



document.addEventListener("DOMContentLoaded", function() {
    var avatarHidden = document.querySelector(".avatar_hiddent");

    if (avatarHidden && avatarHidden.textContent) {
        var avatar = avatarHidden.textContent;
        var imageUrl = "http://localhost:8081/getimage/" + avatar;
        var avatarImage = document.querySelector(".avatar img");
        if (avatarImage) {
            avatarImage.src = imageUrl;
        }
    }
    
   								 //profile
    
            const profileLink = document.getElementById('profile-link');
            const overlay = document.getElementById('overlay');
            const profileForm = document.getElementById('profile-form');

            profileLink.addEventListener('click', function() {
                overlay.style.display = 'block'; 
                profileForm.style.display = 'block'; 
            });
               overlay.addEventListener('click', function(event) {
                if (!profileForm.contains(event.target)) { 
                    overlay.style.display = 'none';
                    profileForm.style.display = 'none';
                }
            });
            
            
            
            
            
            
            //change value img

            profileLink.addEventListener('click', function() {
                overlay.style.display = 'block';
                profileForm.style.display = 'block';
            });

            overlay.addEventListener('click', function(event) {
                if (!profileForm.contains(event.target)) { // Kiểm tra xem sự kiện click có xảy ra bên trong form không
                    overlay.style.display = 'none';
                    profileForm.style.display = 'none';
                }
            });

            const avatarImg = document.querySelector('.avatar img');
            const largeAvatarImg = document.getElementById('avatar-large');
            const smallAvatarImg = document.getElementById('avatar-small');

            largeAvatarImg.src = avatarImg.src;
            smallAvatarImg.src = avatarImg.src;

});