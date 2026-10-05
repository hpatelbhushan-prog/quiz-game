import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Main extends JFrame {

    Color darkBlue = new Color(25, 75, 120);
    Color purple = new Color(90, 65, 170);
    Color green = new Color(130, 220, 20);
    Color darkGreen = new Color(60, 120, 10);
    Color orange = new Color(255, 170, 20);
    Color yellow = new Color(255, 210, 40);

    Map<String, ArrayList<Question>> categories = new HashMap<>();
    ArrayList<Question> quizQuestions = new ArrayList<>();

    int currentQuestion = 0;
    int score = 0;
    int timeLeft = 15;

    Timer timer;
    String playerName = "";
    String selectedCategory = "";
    String selectedAnswer = "";

    JPanel mainPanel;
    JLabel questionLabel, scoreLabel, timerLabel, questionNumberLabel;
    JButton[] optionButtons = new JButton[4];
    JButton nextButton;

    public Main() {
        setTitle("GK Quiz Game");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        createQuestions();
        showHomeScreen();
        setVisible(true);
    }

    void showHomeScreen() {
        if (timer != null) timer.stop();

        mainPanel = new GameBackground();
        mainPanel.setLayout(null);

        JPanel profile = new RoundedPanel(25);
        profile.setBackground(new Color(245, 245, 255));
        profile.setBounds(30, 25, 235, 80);
        profile.setLayout(null);

        JLabel avatar = new JLabel();
        avatar.setIcon(loadIcon("user.png", 42, 42));
        avatar.setHorizontalAlignment(SwingConstants.CENTER);
        avatar.setBounds(8, 10, 52, 52);
        profile.add(avatar);

        JLabel playerLabel = new JLabel("PLAYER");
        playerLabel.setFont(new Font("Arial", Font.BOLD, 18));
        playerLabel.setForeground(darkBlue);
        playerLabel.setBounds(70, 10, 150, 25);
        profile.add(playerLabel);

        JLabel levelLabel = new JLabel("LEVEL 1");
        levelLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        levelLabel.setForeground(Color.GRAY);
        levelLabel.setBounds(70, 38, 100, 20);
        profile.add(levelLabel);

        mainPanel.add(profile);

        JLabel title = new JLabel("QUIZ GAME");
        title.setFont(new Font("Arial", Font.BOLD, 60));
        title.setForeground(Color.WHITE);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setBounds(150, 105, 700, 80);
        mainPanel.add(title);

        JLabel gkTitle = new JLabel("GENERAL KNOWLEDGE");
        gkTitle.setFont(new Font("Arial", Font.BOLD, 30));
        gkTitle.setForeground(yellow);
        gkTitle.setHorizontalAlignment(SwingConstants.CENTER);
        gkTitle.setBounds(200, 180, 600, 45);
        mainPanel.add(gkTitle);

        JLabel nameLabel = new JLabel("ENTER YOUR NAME");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        nameLabel.setBounds(300, 235, 400, 30);
        mainPanel.add(nameLabel);

        JTextField nameField = new JTextField();
        nameField.setFont(new Font("Arial", Font.BOLD, 20));
        nameField.setHorizontalAlignment(SwingConstants.CENTER);
        nameField.setBounds(300, 270, 400, 48);
        nameField.setBorder(BorderFactory.createLineBorder(purple, 3));
        mainPanel.add(nameField);

        JButton categoryButton = new JButton("SELECT CATEGORY");
        categoryButton.setFont(new Font("Arial", Font.BOLD, 20));
        categoryButton.setForeground(darkGreen);
        categoryButton.setBackground(green);
        categoryButton.setBounds(350, 350, 300, 60);
        categoryButton.setFocusPainted(false);
        categoryButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        categoryButton.addActionListener(e -> {
            String name = nameField.getText().trim();

            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Please enter your name!",
                        "Name Required",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            playerName = name;
            showCategoryScreen();
        });

        mainPanel.add(categoryButton);

        JLabel info = new JLabel(
                "⭐ 200 GK QUESTIONS  •  10 CATEGORIES  •  5 RANDOM QUESTIONS"
        );
        info.setFont(new Font("Arial", Font.BOLD, 15));
        info.setForeground(Color.WHITE);
        info.setHorizontalAlignment(SwingConstants.CENTER);
        info.setBounds(100, 465, 800, 35);
        mainPanel.add(info);

        JLabel bottom = new JLabel("🏆 CHALLENGE YOURSELF 🏆");
        bottom.setFont(new Font("Arial", Font.BOLD, 18));
        bottom.setForeground(yellow);
        bottom.setHorizontalAlignment(SwingConstants.CENTER);
        bottom.setBounds(250, 515, 500, 40);
        mainPanel.add(bottom);

        setContentPane(mainPanel);
        revalidate();
        repaint();
    }

    void showCategoryScreen() {
        mainPanel = new GameBackground();
        mainPanel.setLayout(null);

        JLabel title = new JLabel("CHOOSE YOUR CATEGORY");
        title.setFont(new Font("Arial", Font.BOLD, 40));
        title.setForeground(Color.WHITE);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setBounds(150, 30, 700, 60);
        mainPanel.add(title);

        JLabel welcome = new JLabel("Welcome, " + playerName + "!");
        welcome.setFont(new Font("Arial", Font.BOLD, 20));
        welcome.setForeground(yellow);
        welcome.setHorizontalAlignment(SwingConstants.CENTER);
        welcome.setBounds(250, 85, 500, 35);
        mainPanel.add(welcome);

        String[] names = {
                "India GK", "World GK", "Science", "Geography", "History",
                "Sports", "Space", "Computer & Technology", "Economy", "Literature & Arts"
        };

        String[] categoryIcons = {
                "india.png", "world.png", "science.png", "geography.png", "history.png",
                "sports.png", "space.png", "technology.png", "economy.png", "literature.png"
        };

        int x1 = 100, x2 = 520, y = 140;

        for (int i = 0; i < names.length; i++) {
            JButton button = new JButton(names[i], loadIcon(categoryIcons[i], 42, 42));
            button.setFont(new Font("Arial", Font.BOLD, 18));
            button.setBackground(Color.WHITE);
            button.setForeground(darkBlue);
            button.setFocusPainted(false);
            button.setBorder(BorderFactory.createLineBorder(purple, 3));
            button.setCursor(new Cursor(Cursor.HAND_CURSOR));
            button.setHorizontalAlignment(SwingConstants.CENTER);
            button.setVerticalAlignment(SwingConstants.CENTER);
            button.setHorizontalTextPosition(SwingConstants.RIGHT);
            button.setVerticalTextPosition(SwingConstants.CENTER);
            button.setIconTextGap(14);

            int row = i / 2;
            button.setBounds(
                    (i % 2 == 0) ? x1 : x2,
                    y + row * 85,
                    360,
                    65
            );

            String category = names[i];

            button.addActionListener(e -> {
                selectedCategory = category;
                startQuiz();
            });

            mainPanel.add(button);
        }

        JButton back = new JButton("BACK", loadIcon("home.png", 22, 22));
        back.setHorizontalTextPosition(SwingConstants.RIGHT);
        back.setIconTextGap(7);
        back.setFont(new Font("Arial", Font.BOLD, 16));
        back.setBounds(50, 570, 150, 50);
        back.setBackground(Color.WHITE);
        back.setForeground(darkBlue);
        back.setFocusPainted(false);
        back.addActionListener(e -> showHomeScreen());
        mainPanel.add(back);

        JLabel info = new JLabel(
                "20 questions available in each category • 5 random questions per quiz"
        );
        info.setFont(new Font("Arial", Font.BOLD, 14));
        info.setForeground(Color.WHITE);
        info.setHorizontalAlignment(SwingConstants.CENTER);
        info.setBounds(250, 570, 650, 40);
        mainPanel.add(info);

        setContentPane(mainPanel);
        revalidate();
        repaint();
    }

    void startQuiz() {
        score = 0;
        currentQuestion = 0;
        selectedAnswer = "";
        quizQuestions.clear();

        ArrayList<Question> selectedQuestions = categories.get(selectedCategory);
        Collections.shuffle(selectedQuestions);

        for (int i = 0; i < 5; i++) {
            quizQuestions.add(selectedQuestions.get(i));
        }

        showQuizScreen();
        showQuestion();
    }

    void showQuizScreen() {
        mainPanel = new GameBackground();
        mainPanel.setLayout(null);

        JLabel title = new JLabel("GK MASTER QUIZ", loadIcon("quiz.png", 32, 32), SwingConstants.LEFT);
        title.setIconTextGap(10);
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setForeground(Color.WHITE);
        title.setBounds(25, 20, 350, 45);
        mainPanel.add(title);

        JLabel category = new JLabel(selectedCategory);
        category.setFont(new Font("Arial", Font.BOLD, 18));
        category.setForeground(yellow);
        category.setHorizontalAlignment(SwingConstants.CENTER);
        category.setBounds(350, 20, 300, 40);
        mainPanel.add(category);

        JLabel player = new JLabel("Player: " + playerName);
        player.setFont(new Font("Arial", Font.BOLD, 16));
        player.setForeground(Color.WHITE);
        player.setBounds(700, 20, 180, 40);
        mainPanel.add(player);

        scoreLabel = new JLabel("Score: 0", loadIcon("trophy.png", 25, 25), SwingConstants.LEFT);
        scoreLabel.setIconTextGap(8);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 18));
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setBounds(25, 70, 180, 35);
        mainPanel.add(scoreLabel);

        timerLabel = new JLabel("15 sec", loadIcon("time.png", 25, 25), SwingConstants.LEFT);
        timerLabel.setIconTextGap(8);
        timerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        timerLabel.setForeground(Color.WHITE);
        timerLabel.setBounds(850, 70, 100, 35);
        mainPanel.add(timerLabel);

        JPanel questionCard = new RoundedPanel(30);
        questionCard.setBackground(new Color(248, 247, 255));
        questionCard.setBounds(60, 115, 880, 180);
        questionCard.setLayout(null);

        questionNumberLabel = new JLabel("QUESTION 1 / 5", loadIcon("question.png", 24, 24), SwingConstants.LEFT);
        questionNumberLabel.setIconTextGap(8);
        questionNumberLabel.setFont(new Font("Arial", Font.BOLD, 16));
        questionNumberLabel.setForeground(purple);
        questionNumberLabel.setBounds(30, 20, 300, 30);
        questionCard.add(questionNumberLabel);

        questionLabel = new JLabel();
        questionLabel.setFont(new Font("Arial", Font.BOLD, 23));
        questionLabel.setForeground(darkBlue);
        questionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        questionLabel.setBounds(30, 60, 820, 90);
        questionCard.add(questionLabel);

        mainPanel.add(questionCard);

        int startY = 325;

        for (int i = 0; i < 4; i++) {
            optionButtons[i] = createOptionButton(i);
            optionButtons[i].setBounds(
                    60 + (i % 2) * 450,
                    startY + (i / 2) * 90,
                    410,
                    65
            );

            final int index = i;
            optionButtons[i].addActionListener(e -> selectAnswer(index));
            mainPanel.add(optionButtons[i]);
        }

        nextButton = new JButton("NEXT", loadIcon("next.png", 24, 24));
        nextButton.setHorizontalTextPosition(SwingConstants.LEFT);
        nextButton.setIconTextGap(8);
        nextButton.setFont(new Font("Arial", Font.BOLD, 20));
        nextButton.setForeground(Color.WHITE);
        nextButton.setBackground(orange);
        nextButton.setBounds(700, 525, 220, 60);
        nextButton.setFocusPainted(false);
        nextButton.addActionListener(e -> submitAnswer());
        mainPanel.add(nextButton);

        JButton home = new JButton("← HOME");
        home.setFont(new Font("Arial", Font.BOLD, 16));
        home.setBounds(60, 530, 150, 50);
        home.setBackground(Color.WHITE);
        home.setForeground(darkBlue);
        home.setFocusPainted(false);
        home.addActionListener(e -> showHomeScreen());
        mainPanel.add(home);

        setContentPane(mainPanel);
        revalidate();
        repaint();
    }

    ImageIcon loadIcon(String file, int width, int height) {
        ImageIcon original = new ImageIcon("images/" + file);
        if (original.getIconWidth() <= 0) {
            return null;
        }
        Image image = original.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(image);
    }

    JButton createOptionButton(int index) {
        JButton button = new JButton();
        button.setFont(new Font("Arial", Font.BOLD, 17));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        Color[] colors = {
                new Color(225, 240, 255),
                new Color(225, 250, 235),
                new Color(255, 245, 215),
                new Color(240, 230, 255)
        };

        button.setBackground(colors[index]);
        button.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 150), 2));
        return button;
    }

    void showQuestion() {
        Question q = quizQuestions.get(currentQuestion);

        questionNumberLabel.setText(
                "QUESTION " + (currentQuestion + 1) + " / 5"
        );

        questionLabel.setText(
                "<html><center>" + q.question + "</center></html>"
        );

        optionButtons[0].setText("   A     " + q.optionA);
        optionButtons[1].setText("   B     " + q.optionB);
        optionButtons[2].setText("   C     " + q.optionC);
        optionButtons[3].setText("   D     " + q.optionD);

        selectedAnswer = "";

        for (JButton button : optionButtons) {
            button.setBorder(
                    BorderFactory.createLineBorder(
                            new Color(100, 100, 150), 2
                    )
            );
        }

        startTimer();
    }

    void selectAnswer(int index) {
        String[] letters = {"A", "B", "C", "D"};
        selectedAnswer = letters[index];

        for (JButton button : optionButtons) {
            button.setBorder(
                    BorderFactory.createLineBorder(
                            new Color(100, 100, 150), 2
                    )
            );
        }

        optionButtons[index].setBorder(
                BorderFactory.createLineBorder(darkBlue, 4)
        );
    }

    void startTimer() {
        if (timer != null) timer.stop();

        timeLeft = 15;
        timerLabel.setText(timeLeft + " sec");

        timer = new Timer(1000, e -> {
            timeLeft--;
            timerLabel.setText(timeLeft + " sec");

            if (timeLeft <= 0) {
                timer.stop();
                submitAnswer();
            }
        });

        timer.start();
    }

    void submitAnswer() {
        if (timer != null) timer.stop();

        Question q = quizQuestions.get(currentQuestion);

        if (selectedAnswer.equals(q.correctAnswer)) {
            score++;
            scoreLabel.setText("🏆 Score: " + score);
        }

        currentQuestion++;

        if (currentQuestion >= 5) {
            showResult();
        } else {
            showQuestion();
        }
    }

    void showResult() {
        if (timer != null) timer.stop();

        mainPanel = new GameBackground();
        mainPanel.setLayout(null);

        JLabel title = new JLabel("QUIZ COMPLETE!", loadIcon("trophy.png", 48, 48), SwingConstants.CENTER);
        title.setIconTextGap(10);
        title.setFont(new Font("Arial", Font.BOLD, 45));
        title.setForeground(Color.WHITE);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setBounds(200, 60, 600, 70);
        mainPanel.add(title);

        JPanel result = new RoundedPanel(35);
        result.setBackground(new Color(248, 247, 255));
        result.setBounds(250, 150, 500, 380);
        result.setLayout(null);

        JLabel player = new JLabel("Well Done, " + playerName + "!");
        player.setFont(new Font("Arial", Font.BOLD, 25));
        player.setForeground(darkBlue);
        player.setHorizontalAlignment(SwingConstants.CENTER);
        player.setBounds(30, 25, 440, 45);
        result.add(player);

        JLabel category = new JLabel(selectedCategory);
        category.setFont(new Font("Arial", Font.BOLD, 18));
        category.setForeground(purple);
        category.setHorizontalAlignment(SwingConstants.CENTER);
        category.setBounds(30, 70, 440, 35);
        result.add(category);

        JLabel scoreText = new JLabel(score + " / 5");
        scoreText.setFont(new Font("Arial", Font.BOLD, 65));
        scoreText.setForeground(purple);
        scoreText.setHorizontalAlignment(SwingConstants.CENTER);
        scoreText.setBounds(30, 105, 440, 90);
        result.add(scoreText);

        JLabel percentage = new JLabel("Your Score: " + (score * 20) + "%");
        percentage.setFont(new Font("Arial", Font.BOLD, 21));
        percentage.setForeground(darkGreen);
        percentage.setHorizontalAlignment(SwingConstants.CENTER);
        percentage.setBounds(30, 195, 440, 40);
        result.add(percentage);

        JButton again = new JButton("PLAY AGAIN", loadIcon("play.png", 24, 24));
        again.setHorizontalTextPosition(SwingConstants.RIGHT);
        again.setIconTextGap(8);
        again.setFont(new Font("Arial", Font.BOLD, 18));
        again.setForeground(darkGreen);
        again.setBackground(green);
        again.setBounds(50, 270, 180, 55);
        again.setFocusPainted(false);
        again.addActionListener(e -> startQuiz());
        result.add(again);

        JButton categoriesButton = new JButton("CATEGORIES", loadIcon("category.png", 24, 24));
        categoriesButton.setHorizontalTextPosition(SwingConstants.RIGHT);
        categoriesButton.setIconTextGap(8);
        categoriesButton.setFont(new Font("Arial", Font.BOLD, 18));
        categoriesButton.setForeground(Color.WHITE);
        categoriesButton.setBackground(orange);
        categoriesButton.setBounds(270, 270, 180, 55);
        categoriesButton.setFocusPainted(false);
        categoriesButton.addActionListener(e -> showCategoryScreen());
        result.add(categoriesButton);

        mainPanel.add(result);

        setContentPane(mainPanel);
        revalidate();
        repaint();
    }

    /*
     * 200 GK QUESTIONS
     * 20 questions in each of 10 categories.
     * The quiz still asks only 5 random questions.
     */
    void createQuestions() {

        addCategory("India GK",
                new String[][]{
                        {"What is the capital of India?", "Mumbai", "New Delhi", "Kolkata", "Chennai", "B"},
                        {"What is the national animal of India?", "Lion", "Tiger", "Elephant", "Leopard", "B"},
                        {"What is the national flower of India?", "Rose", "Lotus", "Lily", "Sunflower", "B"},
                        {"Who wrote the Indian National Anthem?", "Rabindranath Tagore", "Bankim Chandra Chatterjee", "Sarojini Naidu", "Subhash Chandra Bose", "A"},
                        {"Who was the first Prime Minister of India?", "Mahatma Gandhi", "Jawaharlal Nehru", "Sardar Patel", "Rajendra Prasad", "B"},
                        {"Who was the first President of India?", "Rajendra Prasad", "Jawaharlal Nehru", "S. Radhakrishnan", "Zakir Hussain", "A"},
                        {"Which city is known as the Pink City?", "Jaipur", "Jodhpur", "Udaipur", "Agra", "A"},
                        {"Which is the national bird of India?", "Sparrow", "Peacock", "Eagle", "Parrot", "B"},
                        {"Which state has the longest coastline in India?", "Maharashtra", "Gujarat", "Tamil Nadu", "Kerala", "B"},
                        {"Which is the largest Indian state by area?", "Maharashtra", "Rajasthan", "Madhya Pradesh", "Uttar Pradesh", "B"},
                        {"Which is the smallest Indian state by area?", "Goa", "Sikkim", "Tripura", "Manipur", "A"},
                        {"What is the national aquatic animal of India?", "Ganges river dolphin", "Blue whale", "Crocodile", "Turtle", "A"},
                        {"Which is the national tree of India?", "Neem", "Banyan", "Peepal", "Mango", "B"},
                        {"Which Indian city is known as the Silicon Valley of India?", "Hyderabad", "Pune", "Bengaluru", "Chennai", "C"},
                        {"Which is the highest civilian award in India?", "Padma Shri", "Padma Bhushan", "Bharat Ratna", "Padma Vibhushan", "C"},
                        {"What is the national fruit of India?", "Apple", "Mango", "Banana", "Guava", "B"},
                        {"Which Indian state is famous for the Gir National Park?", "Gujarat", "Rajasthan", "Kerala", "Assam", "A"},
                        {"Which festival is known as the festival of lights?", "Holi", "Diwali", "Eid", "Pongal", "B"},
                        {"Which Indian city is famous for the Gateway of India?", "Mumbai", "Delhi", "Kolkata", "Surat", "A"},
                        {"Which is the highest mountain peak entirely within India?", "Nanda Devi", "K2", "Annapurna", "Makalu", "A"}
                });

        addCategory("World GK",
                new String[][]{
                        {"Which is the largest country by area?", "Canada", "Russia", "China", "USA", "B"},
                        {"Which country is known as the Land of the Rising Sun?", "China", "Japan", "Thailand", "South Korea", "B"},
                        {"Which country has the Great Wall?", "India", "Japan", "China", "Korea", "C"},
                        {"Which is the smallest country in the world?", "Monaco", "Vatican City", "Maldives", "Singapore", "B"},
                        {"Which country is famous for the Eiffel Tower?", "Italy", "France", "Germany", "Spain", "B"},
                        {"Which country has the maple leaf on its flag?", "USA", "Canada", "Australia", "UK", "B"},
                        {"What is the capital of France?", "London", "Berlin", "Paris", "Rome", "C"},
                        {"What is the capital of Australia?", "Sydney", "Melbourne", "Canberra", "Perth", "C"},
                        {"Which city is known as the Big Apple?", "London", "New York", "Paris", "Tokyo", "B"},
                        {"Which is the largest island in the world?", "Greenland", "Madagascar", "Borneo", "New Guinea", "A"},
                        {"Which country is shaped like a boot?", "France", "Italy", "Greece", "Portugal", "B"},
                        {"Which country is famous for the pyramids of Giza?", "Egypt", "Mexico", "Peru", "Jordan", "A"},
                        {"What is the capital of Japan?", "Kyoto", "Tokyo", "Osaka", "Hiroshima", "B"},
                        {"What is the capital of Canada?", "Toronto", "Vancouver", "Ottawa", "Montreal", "C"},
                        {"Which country is known for the Statue of Liberty?", "USA", "UK", "France", "Canada", "A"},
                        {"Which country has the city of Dubai?", "Qatar", "Saudi Arabia", "UAE", "Oman", "C"},
                        {"What is the capital of Germany?", "Munich", "Frankfurt", "Berlin", "Hamburg", "C"},
                        {"Which country is famous for the Colosseum?", "Spain", "Italy", "France", "Greece", "B"},
                        {"Which country is home to Machu Picchu?", "Chile", "Peru", "Brazil", "Bolivia", "B"},
                        {"What is the capital of Italy?", "Milan", "Venice", "Rome", "Naples", "C"}
                });

        addCategory("Science",
                new String[][]{
                        {"Which gas is most abundant in Earth's atmosphere?", "Oxygen", "Nitrogen", "Carbon Dioxide", "Hydrogen", "B"},
                        {"Which organ pumps blood throughout the human body?", "Brain", "Lungs", "Heart", "Kidney", "C"},
                        {"Which metal is liquid at room temperature?", "Iron", "Mercury", "Copper", "Aluminium", "B"},
                        {"How many bones are there in an adult human body?", "196", "206", "216", "226", "B"},
                        {"Which vitamin is mainly obtained from sunlight?", "Vitamin A", "Vitamin B", "Vitamin C", "Vitamin D", "D"},
                        {"What is H2O commonly known as?", "Salt", "Water", "Oxygen", "Hydrogen", "B"},
                        {"What is the boiling point of water at sea level?", "50°C", "75°C", "100°C", "150°C", "C"},
                        {"What is the freezing point of water?", "0°C", "10°C", "50°C", "100°C", "A"},
                        {"Which is the hardest natural substance?", "Gold", "Iron", "Diamond", "Silver", "C"},
                        {"How many chambers does the human heart have?", "2", "3", "4", "5", "C"},
                        {"Which planet is known as the Red Planet?", "Venus", "Mars", "Jupiter", "Mercury", "B"},
                        {"What is the chemical symbol for gold?", "Ag", "Au", "Fe", "Gd", "B"},
                        {"What force pulls objects toward Earth?", "Magnetism", "Gravity", "Friction", "Pressure", "B"},
                        {"What is the SI unit of electric current?", "Volt", "Ohm", "Ampere", "Watt", "C"},
                        {"Which organ is mainly responsible for filtering blood?", "Heart", "Kidney", "Lung", "Stomach", "B"},
                        {"What is the largest organ of the human body?", "Liver", "Skin", "Heart", "Brain", "B"},
                        {"Which gas do plants absorb during photosynthesis?", "Oxygen", "Nitrogen", "Carbon dioxide", "Hydrogen", "C"},
                        {"What is the center of an atom called?", "Electron", "Nucleus", "Proton", "Shell", "B"},
                        {"Which blood cells help fight infections?", "Red blood cells", "White blood cells", "Platelets", "Plasma", "B"},
                        {"What is the approximate speed of light in vacuum?", "3 × 10^8 m/s", "3 × 10^5 m/s", "3 × 10^3 m/s", "3 × 10^10 m/s", "A"}
                });

        addCategory("Geography",
                new String[][]{
                        {"Which is the largest ocean in the world?", "Atlantic Ocean", "Indian Ocean", "Pacific Ocean", "Arctic Ocean", "C"},
                        {"Which is the smallest continent?", "Asia", "Europe", "Australia", "Africa", "C"},
                        {"Which is the largest hot desert in the world?", "Gobi", "Sahara", "Kalahari", "Thar", "B"},
                        {"Which continent is known as the Dark Continent?", "Asia", "Africa", "Europe", "Australia", "B"},
                        {"How many continents are there?", "5", "6", "7", "8", "C"},
                        {"How many oceans are there?", "3", "4", "5", "6", "C"},
                        {"Which is the highest mountain in the world?", "K2", "Mount Everest", "Kangchenjunga", "Makalu", "B"},
                        {"Which is the longest river in India?", "Yamuna", "Ganga", "Godavari", "Narmada", "B"},
                        {"Which is the deepest ocean?", "Atlantic", "Pacific", "Indian", "Arctic", "B"},
                        {"Which city is known as the City of Canals?", "Venice", "Rome", "Madrid", "Berlin", "A"},
                        {"Which is the longest river in the world by commonly cited length?", "Amazon", "Nile", "Yangtze", "Mississippi", "B"},
                        {"Which is the largest continent?", "Africa", "Asia", "Europe", "North America", "B"},
                        {"Which imaginary line divides Earth into Northern and Southern Hemispheres?", "Prime Meridian", "Equator", "Tropic of Cancer", "Arctic Circle", "B"},
                        {"Which imaginary line is at 0° longitude?", "Equator", "Prime Meridian", "Tropic of Capricorn", "International Date Line", "B"},
                        {"Which is the largest ocean after the Pacific?", "Indian", "Atlantic", "Arctic", "Southern", "B"},
                        {"Which country has the most natural lakes?", "India", "Canada", "Brazil", "Russia", "B"},
                        {"Which desert covers much of northern Africa?", "Atacama", "Sahara", "Gobi", "Thar", "B"},
                        {"Which mountain range separates Europe and Asia?", "Andes", "Alps", "Ural Mountains", "Rockies", "C"},
                        {"Which is the largest freshwater lake by surface area?", "Lake Victoria", "Lake Superior", "Lake Baikal", "Caspian Sea", "B"},
                        {"Which river flows through Egypt?", "Nile", "Amazon", "Danube", "Ganges", "A"}
                });

        addCategory("History",
                new String[][]{
                        {"Who built the Taj Mahal?", "Akbar", "Shah Jahan", "Aurangzeb", "Babur", "B"},
                        {"Where is the Taj Mahal located?", "Delhi", "Agra", "Jaipur", "Lucknow", "B"},
                        {"Who founded the Maurya Empire?", "Ashoka", "Chandragupta Maurya", "Harsha", "Akbar", "B"},
                        {"Who is known as the Iron Man of India?", "Sardar Vallabhbhai Patel", "Jawaharlal Nehru", "Subhash Chandra Bose", "Bhagat Singh", "A"},
                        {"When is India's Independence Day celebrated?", "26 January", "15 August", "2 October", "14 November", "B"},
                        {"When is India's Republic Day celebrated?", "15 August", "26 January", "2 October", "5 September", "B"},
                        {"Who is known as the Missile Man of India?", "C. V. Raman", "A. P. J. Abdul Kalam", "Homi Bhabha", "Vikram Sarabhai", "B"},
                        {"Who was the first person to walk on the Moon?", "Neil Armstrong", "Yuri Gagarin", "Buzz Aldrin", "Michael Collins", "A"},
                        {"Who is traditionally credited with discovering gravity?", "Albert Einstein", "Isaac Newton", "Galileo Galilei", "Nikola Tesla", "B"},
                        {"Who invented the telephone?", "Thomas Edison", "Alexander Graham Bell", "Nikola Tesla", "James Watt", "B"},
                        {"Who was known as the Father of the Indian Constitution?", "B. R. Ambedkar", "Mahatma Gandhi", "Rajendra Prasad", "Sardar Patel", "A"},
                        {"The Battle of Plassey was fought in which year?", "1757", "1764", "1857", "1947", "A"},
                        {"Who founded the Mughal Empire in India?", "Akbar", "Babur", "Humayun", "Shah Jahan", "B"},
                        {"Who was the first Mughal emperor?", "Akbar", "Babur", "Aurangzeb", "Humayun", "B"},
                        {"Who gave the slogan 'Jai Jawan Jai Kisan'?", "Lal Bahadur Shastri", "Jawaharlal Nehru", "Indira Gandhi", "Rajendra Prasad", "A"},
                        {"Who led the Dandi March in 1930?", "Mahatma Gandhi", "Sardar Patel", "Subhash Chandra Bose", "Bhagat Singh", "A"},
                        {"Which ancient civilization developed around the Indus River?", "Roman Civilization", "Indus Valley Civilization", "Greek Civilization", "Mayan Civilization", "B"},
                        {"Who was the first emperor of the Mauryan Empire?", "Ashoka", "Chandragupta Maurya", "Bindusara", "Samudragupta", "B"},
                        {"Who was known as the Nightingale of India?", "Sarojini Naidu", "Annie Besant", "Vijaya Lakshmi Pandit", "Indira Gandhi", "A"},
                        {"Who founded the Indian National Congress in 1885 with other leaders?", "A. O. Hume", "Mahatma Gandhi", "Jawaharlal Nehru", "Dadabhai Naoroji", "A"}
                });

        addCategory("Sports",
                new String[][]{
                        {"How many players are there in a cricket team?", "9", "10", "11", "12", "C"},
                        {"Which sport is associated with Wimbledon?", "Football", "Cricket", "Tennis", "Hockey", "C"},
                        {"How many rings are there in the Olympic symbol?", "4", "5", "6", "7", "B"},
                        {"Which sport uses a shuttlecock?", "Tennis", "Badminton", "Table Tennis", "Hockey", "B"},
                        {"How many players are there in a football team on the field?", "9", "10", "11", "12", "C"},
                        {"Which sport is known as the gentleman's game?", "Football", "Cricket", "Hockey", "Tennis", "B"},
                        {"Who is known as the Flying Sikh of India?", "Milkha Singh", "Kapil Dev", "Dhyan Chand", "P. T. Usha", "A"},
                        {"Which chess piece moves in an L shape?", "Bishop", "Knight", "Rook", "Queen", "B"},
                        {"How many squares are there on a chessboard?", "32", "48", "64", "72", "C"},
                        {"Which sport uses a racket and a net?", "Badminton", "Football", "Cricket", "Boxing", "A"},
                        {"How many players are there in a volleyball team on court?", "5", "6", "7", "8", "B"},
                        {"Which country hosted the first modern Olympic Games in 1896?", "France", "Greece", "UK", "USA", "B"},
                        {"In which sport is the term 'checkmate' used?", "Chess", "Boxing", "Golf", "Tennis", "A"},
                        {"How many holes are played in a standard round of golf?", "9", "12", "18", "24", "C"},
                        {"Which sport has a 'free throw'?", "Basketball", "Cricket", "Tennis", "Swimming", "A"},
                        {"Which country is famous for sumo wrestling?", "China", "Japan", "Korea", "Thailand", "B"},
                        {"What color card means a player is sent off in football?", "Yellow", "Blue", "Red", "Green", "C"},
                        {"Which sport is played at the Tour de France?", "Cycling", "Running", "Swimming", "Motor Racing", "A"},
                        {"What is the maximum score with one dart in darts?", "50", "60", "75", "100", "B"},
                        {"Which sport uses a puck?", "Ice Hockey", "Field Hockey", "Cricket", "Rugby", "A"}
                });

        addCategory("Space",
                new String[][]{
                        {"Which is the largest planet in our Solar System?", "Earth", "Mars", "Jupiter", "Saturn", "C"},
                        {"Which planet is known as the Red Planet?", "Venus", "Mars", "Jupiter", "Mercury", "B"},
                        {"Which planet is closest to the Sun?", "Venus", "Earth", "Mercury", "Mars", "C"},
                        {"Which planet is famous for its rings?", "Mars", "Saturn", "Venus", "Mercury", "B"},
                        {"Which planet is known as Earth's twin?", "Mars", "Venus", "Jupiter", "Mercury", "B"},
                        {"How many planets are in our Solar System?", "7", "8", "9", "10", "B"},
                        {"Which is the hottest planet?", "Mercury", "Venus", "Mars", "Jupiter", "B"},
                        {"What is the natural satellite of Earth?", "Sun", "Moon", "Mars", "Venus", "B"},
                        {"Which galaxy contains our Solar System?", "Andromeda", "Milky Way", "Whirlpool", "Sombrero", "B"},
                        {"What is the study of stars and planets called?", "Biology", "Astronomy", "Geology", "Ecology", "B"},
                        {"Which planet is farthest from the Sun?", "Uranus", "Neptune", "Saturn", "Jupiter", "B"},
                        {"Which planet rotates on its side?", "Mars", "Uranus", "Earth", "Venus", "B"},
                        {"What is the largest natural satellite in the Solar System?", "Moon", "Ganymede", "Titan", "Europa", "B"},
                        {"What was the first artificial satellite called?", "Apollo 11", "Sputnik 1", "Voyager 1", "Luna 2", "B"},
                        {"Which space agency launched the Chandrayaan missions?", "NASA", "ISRO", "ESA", "JAXA", "B"},
                        {"What does ISRO stand for?", "Indian Space Research Organisation", "International Space Research Office", "Indian Satellite Research Organisation", "International Satellite Research Organisation", "A"},
                        {"Which planet has the Great Red Spot?", "Saturn", "Jupiter", "Neptune", "Mars", "B"},
                        {"What is a group of stars forming a recognizable pattern called?", "Galaxy", "Constellation", "Asteroid", "Comet", "B"},
                        {"What is the Sun?", "A planet", "A star", "A moon", "An asteroid", "B"},
                        {"Which planet has the most prominent ring system?", "Earth", "Saturn", "Mars", "Mercury", "B"}
                });

        addCategory("Computer & Technology",
                new String[][]{
                        {"What does CPU stand for?", "Central Processing Unit", "Computer Personal Unit", "Central Program Utility", "Computer Processing Utility", "A"},
                        {"Which device is used to type text?", "Monitor", "Keyboard", "Speaker", "Printer", "B"},
                        {"Which device displays computer output?", "Keyboard", "Mouse", "Monitor", "Scanner", "C"},
                        {"What does RAM stand for?", "Random Access Memory", "Read Access Memory", "Rapid Access Machine", "Random Application Memory", "A"},
                        {"Which is an operating system?", "Windows", "Google", "Intel", "Oracle", "A"},
                        {"Which company developed Android?", "Microsoft", "Google", "Apple", "IBM", "B"},
                        {"What does WWW stand for?", "World Wide Web", "World Web Window", "Wide World Web", "Web World Wide", "A"},
                        {"Which language is mainly used to style web pages?", "HTML", "CSS", "Python", "Java", "B"},
                        {"Which one is a programming language?", "Python", "Windows", "Chrome", "Google", "A"},
                        {"What does USB stand for?", "Universal Serial Bus", "United System Bus", "Universal System Board", "User Serial Board", "A"},
                        {"What does HTML stand for?", "HyperText Markup Language", "HighText Machine Language", "Hyperlink Text Management Language", "Home Tool Markup Language", "A"},
                        {"What does URL stand for?", "Uniform Resource Locator", "Universal Reference Link", "Uniform Routing Language", "User Resource Locator", "A"},
                        {"Which device is used to move the pointer on a computer?", "Mouse", "Keyboard", "Printer", "Router", "A"},
                        {"Which company developed Windows?", "Apple", "Microsoft", "Google", "IBM", "B"},
                        {"What is the brain of a computer commonly called?", "CPU", "Monitor", "Keyboard", "RAM", "A"},
                        {"Which storage device has no moving parts and is generally faster than an HDD?", "SSD", "Floppy Disk", "CD", "Tape", "A"},
                        {"What does PDF stand for?", "Portable Document Format", "Personal Data File", "Printed Document Form", "Public Document File", "A"},
                        {"Which protocol is commonly used for secure web browsing?", "HTTP", "FTP", "HTTPS", "SMTP", "C"},
                        {"What does AI stand for?", "Automated Internet", "Artificial Intelligence", "Advanced Interface", "Applied Information", "B"},
                        {"What does IoT stand for?", "Internet of Things", "Input of Technology", "Internet of Technology", "Interface of Things", "A"}
                });

        addCategory("Economy",
                new String[][]{
                        {"What is the currency of Japan?", "Dollar", "Yuan", "Yen", "Won", "C"},
                        {"What is the currency of the USA?", "Dollar", "Euro", "Pound", "Yen", "A"},
                        {"What is the currency of the United Kingdom?", "Euro", "Dollar", "Pound Sterling", "Yen", "C"},
                        {"What is the currency of China?", "Yen", "Yuan", "Won", "Dollar", "B"},
                        {"What is the currency of Russia?", "Ruble", "Euro", "Yuan", "Dinar", "A"},
                        {"What does GDP stand for?", "Gross Domestic Product", "General Domestic Product", "Gross Development Price", "General Development Product", "A"},
                        {"What does ATM stand for?", "Automatic Teller Machine", "Automatic Transfer Machine", "Auto Transaction Method", "Advanced Teller Machine", "A"},
                        {"What does RBI stand for?", "Reserve Bank of India", "Regional Bank of India", "Royal Bank of India", "Reserve Banking Institute", "A"},
                        {"Which institution issues currency notes in India?", "SEBI", "RBI", "SBI", "NITI Aayog", "B"},
                        {"What is inflation?", "Fall in prices", "Rise in general price levels", "Increase in production", "Decrease in population", "B"},
                        {"What does GST stand for?", "Goods and Services Tax", "General Sales Tax", "Government Service Tax", "Goods Supply Tax", "A"},
                        {"What does SEBI stand for?", "Securities and Exchange Board of India", "Service Exchange Bank of India", "Stock Exchange Board of India", "Securities Economic Bank of India", "A"},
                        {"What is a budget?", "A plan of income and expenditure", "A bank account", "A tax receipt", "A loan document", "A"},
                        {"What is a bank primarily used for?", "Only printing books", "Financial services", "Making vehicles", "Producing electricity", "B"},
                        {"What is a loan?", "Money borrowed that is generally repaid", "A type of tax", "A gift", "A salary", "A"},
                        {"What is saving?", "Spending all income", "Setting aside part of income", "Borrowing money", "Paying tax", "B"},
                        {"What is a stock exchange?", "A marketplace for securities", "A grocery market", "A currency printing office", "A government school", "A"},
                        {"What is interest on a loan?", "A fee/cost for borrowing money", "A government election", "A type of currency", "A salary", "A"},
                        {"What is a consumer?", "A person or organization that uses goods or services", "Only a producer", "Only a bank", "A tax officer", "A"},
                        {"What is a producer?", "One who creates goods or services", "One who only consumes", "A bank customer only", "A tourist", "A"}
                });

        addCategory("Literature & Arts",
                new String[][]{
                        {"Who wrote Romeo and Juliet?", "William Shakespeare", "Charles Dickens", "Mark Twain", "Leo Tolstoy", "A"},
                        {"Who wrote The Jungle Book?", "Rudyard Kipling", "William Shakespeare", "J. K. Rowling", "George Orwell", "A"},
                        {"Who wrote Harry Potter?", "J. K. Rowling", "Agatha Christie", "Jane Austen", "Virginia Woolf", "A"},
                        {"Who painted the Mona Lisa?", "Vincent van Gogh", "Leonardo da Vinci", "Pablo Picasso", "Michelangelo", "B"},
                        {"Which art form uses colors on a surface?", "Painting", "Sculpture", "Dance", "Music", "A"},
                        {"Who wrote the Ramayana?", "Valmiki", "Kalidasa", "Tulsidas", "Kabir", "A"},
                        {"Who wrote the Mahabharata?", "Valmiki", "Ved Vyasa", "Kalidasa", "Tulsidas", "B"},
                        {"Which instrument has black and white keys?", "Guitar", "Piano", "Violin", "Flute", "B"},
                        {"Which dance form originated in Tamil Nadu?", "Kathak", "Bharatanatyam", "Kathakali", "Manipuri", "B"},
                        {"Which Indian dance form is associated with Kerala?", "Kathakali", "Bharatanatyam", "Kathak", "Garba", "A"},
                        {"Who wrote Pride and Prejudice?", "Jane Austen", "Emily Bronte", "George Eliot", "Virginia Woolf", "A"},
                        {"Who wrote Oliver Twist?", "Charles Dickens", "William Wordsworth", "Mark Twain", "Ernest Hemingway", "A"},
                        {"Who wrote The Odyssey?", "Homer", "Plato", "Aristotle", "Sophocles", "A"},
                        {"Who painted The Starry Night?", "Pablo Picasso", "Vincent van Gogh", "Leonardo da Vinci", "Claude Monet", "B"},
                        {"Which instrument commonly has six strings?", "Flute", "Guitar", "Piano", "Tabla", "B"},
                        {"Which Indian classical dance is strongly associated with Uttar Pradesh?", "Kathak", "Bharatanatyam", "Odissi", "Mohiniyattam", "A"},
                        {"Who wrote the play Hamlet?", "William Shakespeare", "Charles Dickens", "Oscar Wilde", "Homer", "A"},
                        {"Which art involves shaping figures from stone, wood, or metal?", "Sculpture", "Poetry", "Music", "Drama", "A"},
                        {"Who wrote Around the World in Eighty Days?", "Jules Verne", "Mark Twain", "H. G. Wells", "Robert Louis Stevenson", "A"},
                        {"Which Indian festival is strongly associated with Garba dance?", "Holi", "Navratri", "Diwali", "Onam", "B"}
                });
    }

    void addCategory(String category, String[][] data) {
        ArrayList<Question> list = new ArrayList<>();

        for (String[] q : data) {
            list.add(new Question(
                    q[0], q[1], q[2], q[3], q[4], q[5]
            ));
        }

        categories.put(category, list);
    }

    static class Question {
        String question, optionA, optionB, optionC, optionD, correctAnswer;

        Question(
                String question,
                String optionA,
                String optionB,
                String optionC,
                String optionD,
                String correctAnswer
        ) {
            this.question = question;
            this.optionA = optionA;
            this.optionB = optionB;
            this.optionC = optionC;
            this.optionD = optionD;
            this.correctAnswer = correctAnswer;
        }
    }

    static class RoundedPanel extends JPanel {
        int radius;

        RoundedPanel(int radius) {
            this.radius = radius;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            g2.setColor(getBackground());

            g2.fillRoundRect(
                    0, 0,
                    getWidth(), getHeight(),
                    radius, radius
            );

            g2.dispose();
            super.paintComponent(g);
        }
    }

    static class GameBackground extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D) g.create();

            GradientPaint gradient = new GradientPaint(
                    0, 0,
                    new Color(20, 100, 155),
                    0, getHeight(),
                    new Color(80, 190, 220)
            );

            g2.setPaint(gradient);
            g2.fillRect(0, 0, getWidth(), getHeight());

            g2.setColor(new Color(255, 255, 255, 130));

            drawCloud(g2, 80, 120);
            drawCloud(g2, 730, 120);
            drawCloud(g2, 400, 570);

            g2.setColor(new Color(70, 170, 100));
            g2.fillOval(-200, 550, 650, 250);

            g2.setColor(new Color(60, 150, 90));
            g2.fillOval(650, 550, 700, 280);

            g2.setColor(new Color(255, 255, 255, 40));

            g2.fillOval(40, 350, 100, 100);
            g2.fillOval(850, 380, 120, 120);

            g2.dispose();
        }

        void drawCloud(Graphics2D g2, int x, int y) {
            g2.fillOval(x, y + 15, 70, 40);
            g2.fillOval(x + 30, y, 80, 60);
            g2.fillOval(x + 80, y + 15, 70, 40);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main());
    }
}
