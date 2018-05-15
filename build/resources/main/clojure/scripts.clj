(ns clojure.scripts)

;;-----------------------------------------------------------------------------
;; Set up some classes
;;-----------------------------------------------------------------------------
;; Defines a generic machine such as a laser cutter
(defrecord Machine [
    ^String name 
    ^Double overheadRate 
    ^Double operatingCost 
    ^Double laborCost
    ^Double setupTime
])

;; Defines a generic process such as laser cutting
(defrecord MachineProcess [
    ^String name 
    ^Double cycleTime 
    ^Double laborTime 
])

;;-----------------------------------------------------------------------------
;; Define some machine instances
;;-----------------------------------------------------------------------------
(def laserCutter (->Machine "Laser Cutter" 12.0 20.0 15.0 0.5))
(def plasmaCutter (->Machine "Plasma Cutter" 12.0 18.0 15.0 0.75))
(def standardPress (->Machine "Standard Press" 12.0 6.50 15.0 0.25))
(def turretPress (->Machine "Turret Press" 12.0 9.25 15.0 0.5))
;; create a hash list of all available machine types
(def machines {
    "Laser Cutter" laserCutter 
    "Plasma Cutter" plasmaCutter 
    "Standard Press" standardPress 
    "Turret Press"  turretPress
})

;;-----------------------------------------------------------------------------
;; Define some process instances
;;-----------------------------------------------------------------------------
(def laserCutting (->MachineProcess "Laser Cutting" 0.5 15.0))
(def boring (->MachineProcess "Boring" 0.2 15.0))
;; create a hash of all available processes
(def machineProcesses {
    "Laser Cutting" laserCutting 
    "Boring" boring
})

;;-----------------------------------------------------------------------------
;; Initialize some instances from the Java app
;;-----------------------------------------------------------------------------
;; define and set the machine variable. The java app passes the name of a 
;; machine which is then selected from the set of available machines
(def machine nil)
(defn assignMachine [machineName]
    (def machine (get machines machineName))
    (println "Machine name: " machineName)
    (println "Selected machine: " machine)
)

;; define and set the process variable. The java app passes the name of a 
;; process which is then selected from the set of available processes
(def machineProcess nil)
(defn assignProcess [processName]
    (def machineProcess (get machineProcesses processName))
    (println "Process name: " processName)
    (println "Selected process: " machineProcess)
)

;; define and set the part variable. A part is a java class instance that is 
;; referenced by the clojure code
(def part nil)
(defn assignPart [obj]
    (def part obj))

;;-----------------------------------------------------------------------------
;; Define some generic functions
;;-----------------------------------------------------------------------------
;; calculate the surface area of a part
(defn surfaceArea [] 
    (* (.getWidth part) (.getHeight part))
)
;; calculate the volume of the part
(defn volume []
    (* (surfaceArea) (.getThickness part))
)
;; calculate the setup cost for the machine. This is incurred per batch
(defn setupCost []
    (* (:setupTime machine) (:laborCost machine))
)
;; calculate setup cost per part. This is a function of batch size.
(defn setupCostPerPart []
    (/ (setupCost) (.getBatchSize part))
)
;; Labor cost is the cost per hour * cycle time in minutes. Standardized to dollars per minute
(defn laborCostPerPart []
    (/ (* (:laborCost machine) (:cycleTime machineProcess)) 60)
)
;; Operating cost is the machine cost per hour * cycle time in minutes
(defn operatingCostPerPart []
    (/ (* (:laborCost machine) (:cycleTime machineProcess)) 60)
)
;; cost per part is setup cost + labor cost + overhead cost + operating cost
(defn costPerPart []
    (+ (+ (setupCostPerPart) (laborCostPerPart)) (operatingCostPerPart))
)
;; calc the amount of time to process the batch. This is the per-part cycle time (mins)
;; plus the setup time (hours)
(defn processingTime []
    (+ (* (:cycleTime machineProcess) (.getBatchSize part)) (* (:setupTime machine) 60))
)
;; calc the total batch cost
(defn batchCost []
    (* (costPerPart) (.getBatchSize part)) 
)
;; Set the cycle time for the machine. This should update the java instance
; (defn updateMachine [] 
;     (. machine setCycleTime 10.0)); 

