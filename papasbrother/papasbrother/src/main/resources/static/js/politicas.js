document.addEventListener('DOMContentLoaded', function() {
    const questions = document.querySelectorAll('.faq-question');
    
    questions.forEach(question => {
        question.addEventListener('click', () => {
            const answer = question.nextElementSibling;
            const toggleIcon = question.querySelector('.toggle-icon');
            
            // Toggle answer
            answer.classList.toggle('active');
            
            // Animate toggle icon
            if (answer.classList.contains('active')) {
                toggleIcon.textContent = '-';
                answer.style.maxHeight = answer.scrollHeight + 'px';
            } else {
                toggleIcon.textContent = '+';
                answer.style.maxHeight = null;
            }
            
            // Close other open answers
            questions.forEach(otherQuestion => {
                if (otherQuestion !== question) {
                    const otherAnswer = otherQuestion.nextElementSibling;
                    const otherToggle = otherQuestion.querySelector('.toggle-icon');
                    otherAnswer.classList.remove('active');
                    otherToggle.textContent = '+';
                    otherAnswer.style.maxHeight = null;
                }
            });
        });
    });
});