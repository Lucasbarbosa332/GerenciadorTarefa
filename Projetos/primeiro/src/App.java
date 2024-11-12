import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;

public class App {
    

    // Lista para armazenar as tarefas
    private static ArrayList<Tarefa> tarefas = new ArrayList<>();
    private static DefaultListModel<String> taskListModel = new DefaultListModel<>();
    private static DefaultListModel<String> taskFilteredModel = new DefaultListModel<>();
    private static JList<String> taskList = new JList<>(taskFilteredModel);
    private static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);  // Executor para agendar tarefas

    public static void main(String[] args) {
        // Carregar tarefas do arquivo
        carregarTarefas();

        // Cria a janela principal
        JFrame frame = new JFrame("Gerenciador de Tarefas");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 700);
        frame.setLocationRelativeTo(null);  // Centraliza a janela na tela

        // Painel principal com layout de caixa vertical
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        frame.add(panel);

        // Estilo do painel
        panel.setBackground(new Color(242, 242, 242)); // Cor de fundo clara

        // Adicionando título
        JLabel titleLabel = new JLabel("Gerenciador de Tarefas", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(34, 34, 34));
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(20));  // Espaço entre o título e os outros componentes

        // Painel para filtros
        JPanel filterPanel = new JPanel();
        filterPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        String[] priorities = {"Todos", "Baixa", "Média", "Alta"};
        JComboBox<String> filterPriorityComboBox = new JComboBox<>(priorities);
        filterPriorityComboBox.setFont(new Font("Arial", Font.PLAIN, 14));
        filterPriorityComboBox.setPreferredSize(new Dimension(150, 30));
        filterPanel.add(new JLabel("Filtrar por Prioridade:"));
        filterPanel.add(filterPriorityComboBox);
        panel.add(filterPanel);

        // Painel para entrada de dados
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(7, 2, 10, 10));  // Agora com 7 linhas
        inputPanel.setBackground(new Color(242, 242, 242));

        JLabel labelTitle = new JLabel("Título:");
        JTextField titleField = new JTextField(30);
        JLabel labelDescription = new JLabel("Descrição:");
        JTextField descriptionField = new JTextField(30);
        JLabel labelPriority = new JLabel("Prioridade:");
        JComboBox<String> priorityComboBox = new JComboBox<>(priorities);
        JLabel labelDueDate = new JLabel("Data de Vencimento (dd/MM/yyyy):");
        JTextField dueDateField = new JTextField(10);
        JLabel labelTimeLimit = new JLabel("Tempo (minutos):");
        JTextField timeLimitField = new JTextField(5);
        JLabel labelRecurring = new JLabel("Repetir Tarefa?");
        JCheckBox recurringCheckBox = new JCheckBox("Sim");

        labelTitle.setFont(new Font("Arial", Font.PLAIN, 14));
        titleField.setFont(new Font("Arial", Font.PLAIN, 14));
        descriptionField.setFont(new Font("Arial", Font.PLAIN, 14));
        priorityComboBox.setFont(new Font("Arial", Font.PLAIN, 14));
        dueDateField.setFont(new Font("Arial", Font.PLAIN, 14));
        timeLimitField.setFont(new Font("Arial", Font.PLAIN, 14));
        recurringCheckBox.setFont(new Font("Arial", Font.PLAIN, 14));

        inputPanel.add(labelTitle);
        inputPanel.add(titleField);
        inputPanel.add(labelDescription);
        inputPanel.add(descriptionField);
        inputPanel.add(labelPriority);
        inputPanel.add(priorityComboBox);
        inputPanel.add(labelDueDate);
        inputPanel.add(dueDateField);
        inputPanel.add(labelTimeLimit);
        inputPanel.add(timeLimitField);
        inputPanel.add(labelRecurring);
        inputPanel.add(recurringCheckBox);
        panel.add(inputPanel);

        // Botões para adicionar, excluir e concluir tarefa
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton addButton = new JButton("Adicionar Tarefa");
        JButton removeButton = new JButton("Excluir Tarefa");
        JButton markCompletedButton = new JButton("Marcar como Concluída");

        // Estilizando os botões
        addButton.setBackground(new Color(0, 204, 102));
        addButton.setForeground(Color.WHITE);
        addButton.setFont(new Font("Arial", Font.BOLD, 14));
        addButton.setFocusPainted(false);

        removeButton.setBackground(new Color(255, 80, 80));
        removeButton.setForeground(Color.WHITE);
        removeButton.setFont(new Font("Arial", Font.BOLD, 14));
        removeButton.setFocusPainted(false);

        markCompletedButton.setBackground(new Color(255, 215, 0));
        markCompletedButton.setForeground(Color.WHITE);
        markCompletedButton.setFont(new Font("Arial", Font.BOLD, 14));
        markCompletedButton.setFocusPainted(false);

        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(markCompletedButton);
        panel.add(buttonPanel);

        // Adiciona a lista de tarefas
        JScrollPane scrollPane = new JScrollPane(taskList);
        scrollPane.setPreferredSize(new Dimension(600, 200));
        panel.add(scrollPane);

        // Evento para adicionar tarefa
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = titleField.getText();
                String description = descriptionField.getText();
                String priority = (String) priorityComboBox.getSelectedItem();
                String dueDate = dueDateField.getText();
                String timeLimitStr = timeLimitField.getText();
                boolean isRecurring = recurringCheckBox.isSelected();

                if (!title.isEmpty() && !description.isEmpty() && !dueDate.isEmpty() && !timeLimitStr.isEmpty()) {
                    try {
                        int timeLimit = Integer.parseInt(timeLimitStr); // Tempo em minutos
                        Tarefa newTask = new Tarefa(title, description, priority, dueDate, timeLimit, isRecurring);
                        tarefas.add(newTask);
                        updateTaskList();

                        // Agendar a tarefa para ser concluída após o tempo limite
                        scheduler.schedule(new Runnable() {
                            @Override
                            public void run() {
                                newTask.setCompleted(true);
                                updateTaskList();
                                JOptionPane.showMessageDialog(frame, "A tarefa \"" + title + "\" foi concluída automaticamente!");
                            }
                        }, timeLimit, TimeUnit.MINUTES);

                        // Se a tarefa for repetitiva, ela será reprogramada
                        if (isRecurring) {
                            scheduler.schedule(new Runnable() {
                                @Override
                                public void run() {
                                    tarefas.remove(newTask);  // Remove a tarefa antiga
                                    newTask.setCompleted(false);
                                    tarefas.add(newTask);
                                    updateTaskList();
                                    JOptionPane.showMessageDialog(frame, "A tarefa \"" + title + "\" foi agendada novamente!");
                                }
                            }, 1, TimeUnit.DAYS);  // Reprogramar para o dia seguinte
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(frame, "Por favor, insira um número válido para o tempo limite!");
                    }
                    titleField.setText("");
                    descriptionField.setText("");
                    dueDateField.setText("");
                    timeLimitField.setText("");
                } else {
                    JOptionPane.showMessageDialog(frame, "Por favor, preencha todos os campos!");
                }
            }
        });

        // Evento para excluir tarefa
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = taskList.getSelectedIndex();
                if (selectedIndex != -1) {
                    tarefas.remove(selectedIndex);
                    updateTaskList();
                } else {
                    JOptionPane.showMessageDialog(frame, "Selecione uma tarefa para excluir.");
                }
            }
        });

        // Evento para marcar tarefa como concluída
        markCompletedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = taskList.getSelectedIndex();
                if (selectedIndex != -1) {
                    Tarefa selectedTask = tarefas.get(selectedIndex);
                    selectedTask.setCompleted(true);
                    updateTaskList();
                } else {
                    JOptionPane.showMessageDialog(frame, "Selecione uma tarefa para marcar como concluída.");
                }
            }
        });

        // Atualiza a lista de tarefas com a prioridade em cores
        updateTaskList();
        frame.setVisible(true);
    }

    // Atualiza a lista de tarefas na interface
    private static void updateTaskList() {
        taskListModel.clear();
        taskFilteredModel.clear();
        for (Tarefa tarefa : tarefas) {
            String taskText = tarefa.toString();
            if (tarefa.getPriority().equals("Alta")) {
                taskFilteredModel.addElement("🟥 " + taskText);  // Vermelho para alta prioridade
            } else if (tarefa.getPriority().equals("Média")) {
                taskFilteredModel.addElement("🟨 " + taskText);  // Amarelo para média prioridade
            } else {
                taskFilteredModel.addElement("🟩 " + taskText);  // Verde para baixa prioridade
            }
        }
    }

    // Carrega as tarefas do arquivo
    private static void carregarTarefas() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("tarefas.dat"))) {
            tarefas = (ArrayList<Tarefa>) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Salva as tarefas no arquivo
    private static void salvarTarefas() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("tarefas.dat"))) {
            oos.writeObject(tarefas);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Classe que representa uma tarefa
    static class Tarefa implements Serializable {
        private String title;
        private String description;
        private String priority;
        private String dueDate;
        private int timeLimit;
        private boolean completed;
        private boolean isRecurring;  // Tarefa repetitiva

        public Tarefa(String title, String description, String priority, String dueDate, int timeLimit, boolean isRecurring) {
            this.title = title;
            this.description = description;
            this.priority = priority;
            this.dueDate = dueDate;
            this.timeLimit = timeLimit;
            this.completed = false;
            this.isRecurring = isRecurring;
        }

        public String getPriority() {
            return priority;
        }

        public void setCompleted(boolean completed) {
            this.completed = completed;
        }

        @Override
        public String toString() {
            return title + " - " + priority + " - " + (completed ? "Concluída" : "Pendente") + " - Vencimento: " + dueDate;
        }
    }
}


