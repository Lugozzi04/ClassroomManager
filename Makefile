# Variabili
JAVAC = javac
JAR = jar
JAVA = java
SRC_DIR = src
OUT_DIR = out
LIB_DIR = lib
MAIN_CLASS = App
MANIFEST = manifest.txt
JAR_FILE = ClassroomManager.jar
LIB_JAR = $(LIB_DIR)/jdatepicker-1.3.4.jar
SRC_FILES := $(shell find $(SRC_DIR) -name "*.java")

# Regole
all: compile jar

compile:
	mkdir -p $(OUT_DIR)
	$(JAVAC) -cp "$(LIB_JAR)" -d $(OUT_DIR) $(SRC_FILES)

jar: compile
	$(JAR) cfm $(JAR_FILE) $(MANIFEST) -C $(OUT_DIR) .

run: jar
	$(JAVA) -jar $(JAR_FILE)

clean:
	rm -rf $(OUT_DIR)
	mkdir -p $(OUT_DIR)
	rm -f $(JAR_FILE)

.PHONY: all compile jar run clean
